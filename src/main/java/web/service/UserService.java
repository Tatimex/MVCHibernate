package web.service;

import web.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(long id);

    void save(User user);

    void updateUser(User user);

    void removeUser(long id);
}
