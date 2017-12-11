package list;

import entity.User;

import java.util.ArrayList;

public class UserList {
    private static volatile UserList instance;
    private ArrayList<User> users;

    private UserList() {
        this.users = new ArrayList<>();
    }

    public static UserList getInstance() {
        UserList localInstance = instance;
        if (localInstance == null) {
            synchronized (UserList.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserList();
                }
            }
        }
        return localInstance;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    
    public User getUserByName(String name) {
        for (User user : getUsers())
            if(user.getName().equals(name)) return user;
        throw new IllegalArgumentException("Cannot find user with name: " + name);
    }
}