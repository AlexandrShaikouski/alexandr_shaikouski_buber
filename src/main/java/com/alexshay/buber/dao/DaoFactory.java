package com.alexshay.buber.dao;

import com.alexshay.buber.dao.exception.DaoException;

import java.io.Serializable;

/**
 * Dao Factory
 */
public interface DaoFactory {
    /**
     * Return implementation of DAO for entity class
     * @param entityClass - entity class
     * @return - implementation of DAO for entity class
     * @throws DaoException - should be clarify
     */
    <T extends Identified<PK>, PK extends Serializable> GenericDao getDao(Class<T> entityClass) throws DaoException;
}