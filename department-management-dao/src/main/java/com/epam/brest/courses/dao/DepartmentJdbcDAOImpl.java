package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DepartmentJdbcDAOImpl implements DepartmentDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentJdbcDAOImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${department.selectAll}")
    private String selectAll;

    @Value("${department.selectById}")
    private String selectById;

    @Value("${department.insert}")
    private String insert;

    @Value("${department.update}")
    private String update;

    @Value("${department.delete}")
    private String delete;

    public DepartmentJdbcDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> getDepartments() {
        LOGGER.debug("Get all departments");
        List<Department> departments = namedParameterJdbcTemplate.query(selectAll, new DepartmentRowMapper());
        return departments;
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        LOGGER.debug("Get departments by ID {}", departmentId);
        Department department = namedParameterJdbcTemplate.queryForObject(selectById,
                new MapSqlParameterSource("departmentId", departmentId),
                new DepartmentRowMapper());
        return department;
    }

    @Override
    public int addDepartment(Department department) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentName", department.getDepartmentName());
        return namedParameterJdbcTemplate.update(insert, paramMap);
    }

    @Override
    public void updateDepartment(Department department) {
        SqlParameterSource namedPArameters = new MapSqlParameterSource()
                .addValue("departmentName", department.getDepartmentName())
                .addValue("departmentId", department.getDepartmentId());
        int status = namedParameterJdbcTemplate.update(update, namedPArameters);
        if (status != 0) {
            LOGGER.debug("Department data updated for ID {}", department.getDepartmentId());
        } else {
            LOGGER.debug("No department found with ID {}", department.getDepartmentId());
        }
    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("departmentId", departmentId);
        int status = namedParameterJdbcTemplate.update(delete, namedParameters);
        if (status != 0) {
            LOGGER.debug("Department data deleted for ID {}", departmentId);
        } else {
            LOGGER.debug("No department found with ID {}", departmentId);
        }

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
