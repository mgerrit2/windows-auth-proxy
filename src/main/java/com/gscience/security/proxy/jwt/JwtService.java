package com.gscience.security.proxy.jwt;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@SuppressWarnings("java:S1874")
@Log4j2
@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey; // Loaded from application.properties

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration; // e.g., 86400000 ms (24 hours)

    @Value("${application.security.jwt.audience}")
    private String jwtAudience;

    @Value("${application.security.jwt.key-id}")
    private String keyId;

    // New: Inject the Authorized Party ID
    @Value("${application.security.jwt.authorized-party}")
    private String authorizedParty;

    // New: Inject the custom azpacr value
    @Value("${application.security.jwt.azpacr.value}")
    private String azpacrValue;

    // New: Inject the Request History/Session Hint value
    @Value("${application.security.jwt.request-history}")
    private String requestHistory;

    // New: Inject the Tenant ID
    @Value("${application.security.jwt.tenant-id}")
    private String tenantId;

    // New: Inject the Token Version
    @Value("${application.security.jwt.token-version}")
    private String tokenVersion;

    // New: Inject the custom xms_ftd value
    @Value("${application.security.jwt.xms-ftd}")
    private String xmsFtd; // Note: usin


}
