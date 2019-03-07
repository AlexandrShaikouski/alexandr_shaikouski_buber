package com.alexshay.buber.dao;

import com.alexshay.buber.dao.exception.DaoException;

import java.io.Serializable;
import java.util.List;

/**
 * Generic DAO
 * @param <T> - Identified entity
 * @param <PK> - Entity primary key
 */
public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {
    /**
     * Save identified entity in DB
     * @param object identified entity
     * @return identified entity in DB
     * @throws DaoException should be clarify
     */
    T persist(T object) throws DaoException;

    /**
     * Get identified entity by PK
     * @param id id
     * @return identified entity
     * @throws DaoException should be clarify
     */
    T getByPK(PK id) throws DaoException;

    /**
     * Update identified entity
     * @param object identified entity
     * @throws DaoException should be clarify
     */
    void update(T object) throws DaoException;

    /**
     * Delete identified entity
     * @param object identified entity
     * @throws DaoException should be clarify
     */
    void delete(T object) throws DaoException;

    /**
     * Get all identified entity
     * @return identified entity
     * @throws DaoException should be clarify
     */
    List<T> getAll() throws DaoException;
}
