package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;

import java.util.List;

public interface DepartmentDAO {

    List<Department> getDepartments();

    Department getDepartmentById(Integer departmentId);

    int addDepartment(Department department);

    void updateDepartment(Department department);

    void deleteDepartment(Integer departmentId);




}
