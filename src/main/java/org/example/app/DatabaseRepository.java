package org.example.app;

import lombok.Getter;
import org.example.infrastructure.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Component
@Log
public class DatabaseRepository implements UserRepository {

    @Getter
    @Env
    private String databaseUrl;

    private List<User> users = new ArrayList<>();




    public DatabaseRepository() {
        System.out.println("DatabaseRepository constructor call");
    }


    @PostConstruct
    public void secondPhaseConstructor(){
        System.out.println("DatabaseRepository secondPhaseConstructor call");
        System.out.println(databaseUrl);
    }

    @Override
    public void save(User user) {
        System.out.println("Some operation is being done in the database to add the user");
        users.add(user);
        System.out.println("Addition complete");
    }

    @Override
    @Cacheable
    public User getUser(@CacheKey String username) {
        System.out.println("Searching the database");
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public List<User> getAll() {
        System.out.println("Reading from the database");
        return new ArrayList<>(users);
    }
}