package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml"})
public class DepartmentJdbcDAOImplTest {

    @Autowired
    private DepartmentDAO departmentDAO;

    @Test
    public void getDepartments() {
        List<Department> departments = departmentDAO.getDepartments();
        assertNotNull(departments);
    }

    @Test
    public void getDepartmentById() {
        Department department = departmentDAO.getDepartmentById(4);
        Department departmentTest = new Department();
        departmentTest.setDepartmentId(4);
        departmentTest.setDepartmentName("HR");
        assertEquals(department.getDepartmentId(), departmentTest.getDepartmentId());
        assertEquals(department.getDepartmentName(), departmentTest.getDepartmentName());
    }

    @Test
    public void addDepartment() {
    }

    @Test
    public void updateDepartment() {
    }

    @Test
    public void deleteDepartment() {
    }
}