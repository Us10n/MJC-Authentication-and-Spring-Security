package com.epam.esm.repository.dao.impl;

import com.epam.esm.domain.entity.Order;
import com.epam.esm.domain.entity.OrderDetail;
import com.epam.esm.repository.dao.OrderDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * The type Order dao.
 */
@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private static final String FIND_ORDERS_BY_USER_ID = "SELECT o FROM Order o WHERE o.user.id = :userId";
    private static final String COUNT_ENTITIES_QUERY = "SELECT count(o) FROM OrderDetail o";
    private static final String ID = "id";

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Order create(Order object) {
        entityManager.persist(object);
        return object;
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public List<Order> findAll(int page, int limit) {
        int offset = (page - 1) * limit;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class).where();
        Root<Order> from = query.from(Order.class);
        CriteriaQuery<Order> criteriaQuery = query.select(from);
        criteriaQuery.orderBy(criteriaBuilder.asc(from.get(ID)));

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long countAll() {
        return entityManager.createQuery(COUNT_ENTITIES_QUERY, Long.class).getSingleResult();
    }

    @Override
    public List<Order> findOrdersByUserId(long id, Integer page, Integer limit) {
        int offset = (page - 1) * limit;

        return entityManager.createQuery(FIND_ORDERS_BY_USER_ID, Order.class)
                .setParameter("userId", id)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
