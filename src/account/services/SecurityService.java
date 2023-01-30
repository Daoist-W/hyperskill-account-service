package account.services;

import account.data.dtos.SecurityEvent;

import java.util.List;

public interface SecurityService {
    public SecurityEvent registerEvent(SecurityEvent securityEvent);

    public List<SecurityEvent> getAllSecurityEvents();

}
