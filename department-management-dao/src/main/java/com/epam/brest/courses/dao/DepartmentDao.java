package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentDao {

    /**
     * Find all departments.
     *
     * @return departments list.
     */
    List<Department> findAll();

    /**
     * Find department by Id.
     *
     * @param departmentId department Id.
     * @return department
     */
    Optional<Department> findById(Integer departmentId);

    /**
     * Persist new department.
     *
     * @param department department.
     * @return persisted department id.
     */
    int create(Department department);

    /**
     * Update department.
     *
     * @param department department.
     * @return number of updated records in the database.
     */
    int update(Department department);

    /**
     * Delete department.
     *
     * @param departmentId department id.
     * @return number of updated records in the database.
     */
    int delete(Integer departmentId);




}
