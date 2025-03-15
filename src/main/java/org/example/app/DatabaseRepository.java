package org.example.app;

import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Env;
import org.example.infrastructure.annotation.Log;
import org.example.infrastructure.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

@Component
@Log
public class DatabaseRepository implements UserRepository {

    @Env
    private String databaseUrl;

    private List<User> users = new ArrayList<>();




    public DatabaseRepository() {
        System.out.println("DatabaseRepository constructor call");
    }


    @PostConstruct
    public void secondPhaseConstructor(){
        System.out.println("DatabaseRepository secondPhaseConstructor call");
    }

    @Override
    public void save(User user) {
        System.out.println("Some operation is being done in the database to add the user");
        users.add(user);
        System.out.println("Addition complete");
    }

    @Override
    public User getUser(String username) {
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