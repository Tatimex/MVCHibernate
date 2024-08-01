package web.DAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class UserDaoImpl implements UserDAO {

    private static final Logger logger = (Logger) LogManager.getLogger(UserDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        logger.debug("Fetching all users");
        List<User> users = entityManager.createQuery("select u from User u", User.class).getResultList();
        logger.debug("Fetched {} users", users.size());
        return users;
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) {
        logger.debug("Fetching user with id: {}", id);
        User user = entityManager.find(User.class, id);
        if (user == null) {
            logger.warn("User with id {} not found", id);
        } else {
            logger.debug("Fetched user: {}", user);
        }
        return user;
    }

    @Transactional
    @Override
    public void save(User user) {
        logger.debug("Saving user: {}", user);
        entityManager.persist(user);
        logger.info("User saved: {}", user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        logger.debug("Updating user: {}", user);
        User existingUser = entityManager.find(User.class, user.getId());
        if (existingUser == null) {
            logger.error("User with id {} not found", user.getId());
            throw new EntityNotFoundException("User with id " + user.getId() + " not found");
        }
        existingUser.setName(user.getName());
        existingUser.setAge(user.getAge());
        entityManager.merge(existingUser);
        logger.info("User updated: {}", existingUser);
    }

    @Transactional
    @Override
    public void removeUser(long id) {
        logger.debug("Removing user with id: {}", id);
        User user = getUserById(id);
        if (user != null) {
            entityManager.remove(user);
            logger.info("User removed: {}", user);
        } else {
            logger.warn("User with id {} not found for removal", id);
        }
    }
}
