package com.epam.esm.repository.dao.impl;

import com.epam.esm.domain.entity.User;
import com.epam.esm.repository.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * The type User dao.
 */
@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private static final String COUNT_ENTITIES_HQUERY = "SELECT count(u) FROM User u";
    private static final String ID = "id";

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll(int page, int limit) {
        int offset = (page - 1) * limit;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> from = query.from(User.class);
        CriteriaQuery<User> criteriaQuery = query.select(from);
        criteriaQuery.orderBy(criteriaBuilder.asc(from.get(ID)));

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long countAll() {
        return entityManager.createQuery(COUNT_ENTITIES_HQUERY, Long.class).getSingleResult();
    }
}
