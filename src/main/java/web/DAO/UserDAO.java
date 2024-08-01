package web.DAO;

import web.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();

    User getUserById(long id);

    void save(User user);

    void updateUser(User user);

    void removeUser(long id);
}
