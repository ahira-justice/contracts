package com.ahirajustice.contracts.common.auditor;

import com.ahirajustice.contracts.common.services.LoggedInService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EntityAuditorAware implements AuditorAware<String> {

    private final LoggedInService loggedInService;

    @Override
    public Optional<String> getCurrentAuditor() {
        try{
            Optional<String> username = loggedInService.getUsername();

            if (username.isEmpty())
                return Optional.of("system");

            return username;
        }
        catch (Exception ex) {
            return Optional.of("system");
        }
    }

}
