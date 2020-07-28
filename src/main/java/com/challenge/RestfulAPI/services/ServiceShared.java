package com.challenge.RestfulAPI.services;

import java.util.List;

/**
 * @Author abdessamadM on 23/06/2020
 */
public interface ServiceShared<A, T>     {

    /**
     * Create a record
     *
     * @param dto record to create
     * @return record created
     */
    A create(A dto);

    /**
     * Update a record
     *
     * @param dto to use to update an existing record
     * @param id  of the record to update
     * @return record updated
     */
    A update(A dto, T id);

    /**
     * Get all records
     *
     * @return records
     */
    List<A> getAll();

    /**
     * Get a record by id
     *
     * @param id the id to use to get the record
     * @return the record
     */
    A getById(T id);

    /**
     * Delete a record by the given id
     *
     * @param id of the record to delete
     */
    void deleteById(T id);
}
