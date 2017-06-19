/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.DAO;

import java.io.Serializable;

/**
 *<p>The EntityDAO interface is generic interface for work with persisted data</p>
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 * @param <T> type of persisted instance
 */
public interface EnityDAO<T extends  Serializable> {
    
    /**
     * Stores instance of <code>T</code> type
     * @param t object to be persisted
     * @return object with DBMS generated keys if any
     */
    T create(T t);

    /**
     * Loads persisted instance of <code>T</code> type from DB, using its id 
     * @param id identifier of stored instance inside the DB
     * @return object with certain id
     */
    T read(long id);

    /**
     * Updates certain object in DB
     * @param t instance of <code>T</code> type, already stored is DB
     * @return true if succeed, false if failed 
     */
    boolean update(T t);
    
    /**
     * Deletes certain object in DB
     * @param t instance of <code>T</code> type, already stored is DB
     * @return true if succeed, false if failed 
     */
    boolean delete(T t);
}
