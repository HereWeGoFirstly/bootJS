package ru.herewego.bootJS.dao;


import org.springframework.stereotype.Component;
import ru.herewego.bootJS.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;


@Component
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Set<Role> setOfRoles() {
        TypedQuery<Role> query = entityManager.createQuery("from Role", Role.class);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public Role getRole(String role) {
        TypedQuery<Role> query = entityManager.createQuery("from Role where authority = :authority", Role.class);
        query.setParameter("authority", role);
        return query.getSingleResult();
    }
}
