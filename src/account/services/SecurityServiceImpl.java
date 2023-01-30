package account.services;

import account.data.dtos.SecurityEvent;
import account.data.entities.SecurityEventEntity;
import account.repo.AccountRepo;
import account.repo.SecurityEventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService{

    @Autowired
    private SecurityEventRepository securityEventRepository;

    @Autowired
    private AccountRepo accountRepo;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public SecurityEvent registerEvent(SecurityEvent securityEvent) {
        SecurityEventEntity savedEntity = securityEventRepository.save(mapper.map(securityEvent, SecurityEventEntity.class));
        return mapper.map(savedEntity, SecurityEvent.class);
    }

    @Override
    public List<SecurityEvent> getAllSecurityEvents() {
        return securityEventRepository.findAll()
                .stream()
                .map(entity -> mapper.map(entity, SecurityEvent.class))
                .collect(Collectors.toList());
    }

}
