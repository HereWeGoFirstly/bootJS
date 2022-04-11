package ru.herewego.bootJS.dao;


import org.springframework.stereotype.Component;
import ru.herewego.bootJS.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@Component
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = entityManager.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public void update(int id, User userChanged) {
        User userToBeChanged = getUserById(id);
        userToBeChanged.setName(userChanged.getName());
        userToBeChanged.setSurname(userChanged.getSurname());
        userToBeChanged.setId(userChanged.getId());
        userToBeChanged.setRoles(userChanged.getRoles());
        userToBeChanged.setPassword(userChanged.getPassword());
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        Query query = entityManager.createQuery("from User where username = :username");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }
}
