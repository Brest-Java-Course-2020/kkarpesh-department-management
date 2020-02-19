package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DepartmentJdbcDAOImpl implements DepartmentDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentJdbcDAOImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartmentJdbcDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> getDepartments() {
        LOGGER.trace("Get all departments");
        String sql = "SELECT d.departmentID, d.departmentName FROM department d ORDER BY d.departmentName";
        List<Department> departments = namedParameterJdbcTemplate.query(sql, new DepartmentRowMapper());
        return departments;
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        String sql = "SELECT d.departmentID, d.departmentName FROM department d WHERE departmentId = :departmentId";
        Department department = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("departmentId", departmentId), new DepartmentRowMapper());
        return department;
    }

    @Override
    public Department addDepartment(Department department) {
        String sql = "INSERT INTO department(departmentName) VALUES (:departmentName)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("departmentName", department.getDepartmentName());

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);

        Integer newDepartmentId = keyHolder.getKey().intValue();
        department.setDepartmentId(newDepartmentId);
        return department;
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
