package org.example.app;


import lombok.Getter;
import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Property;

@Component
@Getter
public class UserAuthenticator {

    @Property("datasource.username")
    private String username;

    @Property
    private String dataSourcePassword;

}