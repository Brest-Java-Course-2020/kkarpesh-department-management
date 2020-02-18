package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentJdbcDAOImpl implements DepartmentDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartmentJdbcDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> getDepartments() {
        List<Department> departments = namedParameterJdbcTemplate.query("SELECT d.departmentID, d.departmentName FROM department d ORDER BY d.departmentName", new DepartmentRowMapper());
        return departments;
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        Department department = namedParameterJdbcTemplate.queryForObject("SELECT d.departmentID, d.departmentName FROM department d WHERE departmentId = :departmentId", new MapSqlParameterSource("departmentId", departmentId), new DepartmentRowMapper());
        return department;
    }

    @Override
    public Department addDepartment(Department department) {
        return null;
    }

    @Override
    public void updateDepartment(Department department) {

    }

    @Override
    public void deleteDepartment(Integer departmentId) {

    }

    private class DepartmentRowMapper implements RowMapper<Department> {

        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setDepartmentId(resultSet.getInt("departmentId"));
            department.setDepartmentName(resultSet.getString("departmentName"));
            return department;
        }
    }
}
