package org.example.app;

import org.example.infrastructure.Application;
import org.example.infrastructure.ApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = Application.run("org.example");

        UserRegistrationService registrationService = context.getObject(UserRegistrationService.class);

        registrationService.register(
                new User(
                        "Gurgen",
                        "gurgen@inconceptlabsc.com",
                        "password123"
                )
        );
        registrationService.register(new User(
                "Arman",
                "somemail@gmail.com",
                "pass111222"
        ));

        //Checking @Property
        UserAuthenticator userAuthenticator = context.getObject(UserAuthenticator.class);
        System.out.println(userAuthenticator.getUsername());
        System.out.println(userAuthenticator.getDataSourcePassword());


        //Checking @Cacheable and @CacheKey
        UserService userService = context.getObject(UserService.class);

        System.out.println(userService.getUser("Gurgen"));
        System.out.println();
        System.out.println(userService.getUser("Gurgen"));
        System.out.println();
        System.out.println(userService.getUser("Arman"));
        System.out.println();
        System.out.println(userService.getUser("Arman"));
        System.out.println();
        System.out.println(userService.getUser("Gurgen"));
    }
}