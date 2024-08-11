package com.ahirajustice.contracts.common.data;

import com.ahirajustice.contracts.common.constants.RoleConstants;
import com.ahirajustice.contracts.common.entities.User;
import com.ahirajustice.contracts.common.properties.AppProperties;
import com.ahirajustice.contracts.common.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    private void seedSuperAdminUser() {
        try {
            Optional<User> superAdminExists = userRepository.findByUsername(appProperties.getSuperuserUsername());

            if (superAdminExists.isPresent())
                return;

            User superAdmin = new User();
            superAdmin.setUsername(appProperties.getSuperuserUsername());
            superAdmin.setEmail(appProperties.getSuperuserEmail());
            superAdmin.setFirstName(appProperties.getSuperuserFirstName());
            superAdmin.setLastName(appProperties.getSuperuserLastName());
            superAdmin.setEmailVerified(true);
            superAdmin.setPassword(passwordEncoder.encode(appProperties.getSuperuserPassword()));
            superAdmin.setRole(RoleConstants.SUPER_ADMIN);

            userRepository.save(superAdmin);
        }
        catch (Exception ignored) {}
    }

    @Override
    public void run(ApplicationArguments args) {
        seedSuperAdminUser();
    }

}
