package com.gscience.security.proxy.service;

import com.gscience.security.proxy.dto.CurrentWindowsDetailDTO;
import com.sun.jna.platform.win32.Secur32;
import com.sun.security.auth.module.NTSystem;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class WindowsUserService {

  public CurrentWindowsDetailDTO currentWindowsUser(){

      NTSystem ntSystem = new NTSystem();

      // 1. Try to get Active Directory / Azure AD Domain UPN via Secur32
      String userEmail = getUserNameExtended(Secur32.EXTENDED_NAME_FORMAT.NameUserPrincipal);

      // 2. Fallback to Deep Registry checks if Secur32 returns empty
      if (StringUtils.isBlank(userEmail)) {
          userEmail = getMicrosoftAccountEmailFromRegistry();
      }

      return CurrentWindowsDetailDTO.builder()
              .verifiedUsername(ntSystem.getName())
              .windowsDomain(ntSystem.getDomain())
              .userSid(ntSystem.getUserSID())
              .userNameExtended(getUserNameExtended(Secur32.EXTENDED_NAME_FORMAT.NameDisplay))
              .userEmail(StringUtils.isNotBlank(userEmail) ? userEmail : "Local Account (No Microsoft Email Linked)")
              .build();


  }


    private  String getUserNameExtended(int nameFormat) {
        char[] nameBuffer = new char[128];
        com.sun.jna.ptr.IntByReference size = new com.sun.jna.ptr.IntByReference(nameBuffer.length);

        boolean result = Secur32.INSTANCE.GetUserNameEx(nameFormat, nameBuffer, size);

        // If buffer was too small, resize it dynamically and try again
        if (!result && size.getValue() > nameBuffer.length) {
            nameBuffer = new char[size.getValue()];
            result = Secur32.INSTANCE.GetUserNameEx(nameFormat, nameBuffer, size);
        }

        if (result) {
            return new String(nameBuffer, 0, size.getValue()).trim();
        }
        return "";
    }

    /**
     * Reads the Microsoft Account email from the volatile environment registry key.
     */
    private String getMicrosoftAccountEmailFromRegistry() {
        try {
            // Query the subkeys under UserExtendedProperties where Windows 11 caches your email
            String[] cmd = {"cmd.exe", "/c", "reg query \"HKCU\\Software\\Microsoft\\IdentityCRL\\UserExtendedProperties\""};
            Process process = Runtime.getRuntime().exec(cmd);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // The email address itself forms part of the registry path line
                    if (line.contains("UserExtendedProperties\\")) {
                        // Extract everything after "UserExtendedProperties\"
                        String[] parts = line.split("UserExtendedProperties\\\\");
                        if (parts.length > 1) {
                            String email = parts[1].trim();
                            // Validation safety check to make sure we grabbed an actual email string
                            if (email.contains("@")) {
                                return email;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Handle fallback gracefully
        }
        return "";
    }

}
