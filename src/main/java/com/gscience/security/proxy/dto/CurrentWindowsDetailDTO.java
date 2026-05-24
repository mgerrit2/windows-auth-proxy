package com.gscience.security.proxy.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CurrentWindowsDetailDTO {

    /**
     * <pre>
     * What it contains: The exact login name of the user on the computer (e.g., gerri).
     *
     * Purpose: This is the unique identifier (the Subject or Username) used to match against your database,
     * or to embed within the JWT token as a unique claim.
     * </pre>
     */
    private String verifiedUsername;

    /**
     * <pre>
     * What it contains: The name of the workstation/computer
     * or the corporate network (e.g., MSI, or a company domain like CORP).
     *
     * Purpose: This allows you to detect whether the user is logged in locally on their own machine,
     * or if the request is originating from an official corporate Active Directory domain.
     * <pre>
     */
    private String windowsDomain;

    /**
     * <pre>
     * What it contains: A long, unique string starting with S-1-5-21-....
     *
     * Purpose: This is the true, immutable passport of a Windows user. Even if a system administrator changes the username from gerri to m.gerrits, the SID remains exactly the same.
     * In enterprise environments,
     * this is the most reliable way to uniquely track and bind a Windows identity.
     * <pre>
     */
    private String userSid;

    private String userNameExtended;

    private String userEmail;

}
