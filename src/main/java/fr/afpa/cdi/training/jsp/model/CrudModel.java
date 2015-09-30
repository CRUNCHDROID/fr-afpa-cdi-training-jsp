/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.afpa.cdi.training.jsp.model;

import java.util.List;

/**
 *
 * @author cdi420
 * @param <T>
 */
public interface CrudModel<T> {

    boolean save(T entity);

    boolean update(T entity);

    T findById(Integer id);

    List<T> findAll();

    boolean delete(T entity);

    boolean delete(Integer id);
}
