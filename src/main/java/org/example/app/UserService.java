package org.example.app;


import org.example.infrastructure.annotation.*;

@Component
public class UserService {

    @Inject
    @Qualifier(DatabaseRepository.class)
    private UserRepository userRepository;

    @PostConstruct
    public void secondaryConstructor(){
        System.out.println("UserService has been initialized");
    }

    public User getUser(String username){
        return userRepository.getUser(username);
    }
}
