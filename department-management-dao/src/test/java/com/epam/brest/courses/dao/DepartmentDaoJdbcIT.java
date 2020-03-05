package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.DepartmentConstants.DEPARTMENT_NAME_SIZE;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
public class DepartmentDaoJdbcIT {

    @Autowired
    private DepartmentDao departmentDAO;

    @Test
    public void shouldFindAllDepartments() {
        List<Department> departments = departmentDAO.findAll();
        assertNotNull(departments);
        assertTrue(departments.size()>0);
    }

    @Test
    public void shouldFindDepartmentById() {

        Department department = new Department();
        department.setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentDAO.create(department);

        Optional<Department> optionalDepartment = departmentDAO.findById(id);

        assertTrue(optionalDepartment.isPresent());
        assertEquals(optionalDepartment.get().getDepartmentId(), id);
        assertEquals(optionalDepartment.get().getDepartmentName(), department.getDepartmentName());


    }

    @Test
    public void shouldCreateDepartment() {
        Department department = new Department();
        department.setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentDAO.create(department);
        assertNotNull(id);
    }

    @Test
    public void shouldUpdateDepartment() {
        Department department = new Department();
        department.setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentDAO.create(department);
        assertNotNull(id);

        Optional<Department> departmentOptional = departmentDAO.findById(id);
        Assertions.assertTrue(departmentOptional.isPresent());

        departmentOptional.get().
                setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));

        int result = departmentDAO.update(departmentOptional.get());

        assertTrue(1 == result);

        Optional<Department> updatedDepartmentOptional = departmentDAO.findById(id);
        Assertions.assertTrue(updatedDepartmentOptional.isPresent());
        assertEquals(updatedDepartmentOptional.get().getDepartmentId(), id);
        assertEquals(updatedDepartmentOptional.get().getDepartmentName(),departmentOptional.get().getDepartmentName());

    }

    @Test
    public void shouldDeleteDepartment() {
        Department department = new Department();
        department.setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentDAO.create(department);

        List<Department> departments = departmentDAO.findAll();
        assertNotNull(departments);

        int result = departmentDAO.delete(id);

        assertTrue(1 == result);

        List<Department> currentDepartments = departmentDAO.findAll();
        assertNotNull(currentDepartments);

        assertTrue(departments.size()-1 == currentDepartments.size());

    }
}