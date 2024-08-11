package com.ahirajustice.contracts.common.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppProperties {

    @Value("${app.config.superuser.email}")
    private String superuserEmail;
    @Value("${app.config.superuser.username}")
    private String superuserUsername;
    @Value("${app.config.superuser.first-name}")
    private String superuserFirstName;
    @Value("${app.config.superuser.last-name}")
    private String superuserLastName;
    @Value("${app.config.superuser.password}")
    private String superuserPassword;

}
