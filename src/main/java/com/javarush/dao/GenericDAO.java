package com.javarush.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class GenericDAO<T> {
    private final Class<T> clazz;
    private final SessionFactory sessionFactory;

    public GenericDAO(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    public List<T> findAll() {
        Session session = getCurrentSession();
        Query<T> query = session.createQuery("from " + clazz.getName(), clazz);
        return query.list();
    }

    public List<T> getItems(int offSet, int count) {
        Session session = getCurrentSession();
        Query<T> query = session.createQuery("from " + clazz.getName(), clazz);
        query.setFirstResult(offSet);
        query.setMaxResults(count);
        return query.list();
    }

    public T getById(Number entityId) {
        Session session = getCurrentSession();
        return session.get(clazz, entityId);
    }

    public T save(T entity) {
        Session session = getCurrentSession();
        session.saveOrUpdate(entity);
        return entity;
    }

    public T update(T entity) {
        Session session = getCurrentSession();
        return (T) session.merge(entity);
    }

    public void deleteById(Integer entityId) {
        Session session = getCurrentSession();
        T entity = getById(entityId);
        delete(entity);
    }

    public void delete(T entity) {
        Session session = getCurrentSession();
        session.remove(entity);
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
