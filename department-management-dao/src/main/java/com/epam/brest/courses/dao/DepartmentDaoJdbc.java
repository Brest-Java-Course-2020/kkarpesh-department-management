package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.brest.courses.constants.DepartmentConstants.*;


public class DepartmentDaoJdbc implements DepartmentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDaoJdbc.class);

    @Value("${department.findAll}")
    private String findAll;

    @Value("${department.findById}")
    private String findById;

    @Value("${department.create}")
    private String create;

    @Value("${department.update}")
    private String update;

    @Value("${department.delete}")
    private String delete;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final DepartmentRowMapper departmentRowMapper = new DepartmentRowMapper();

    public DepartmentDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    @Override
    public List<Department> findAll() {
        LOGGER.debug("Find all departments");

        List<Department> departments = namedParameterJdbcTemplate.query(findAll, departmentRowMapper);
        return departments;
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {
        LOGGER.debug("Find department by ID {}", departmentId);

        SqlParameterSource namedParameters = new MapSqlParameterSource(DEPARTMENT_ID, departmentId);
        List<Department> results = namedParameterJdbcTemplate.query(findById, namedParameters, departmentRowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }

    @Override
    public int create(Department department) {
        LOGGER.debug("create(department:{})", department);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DEPARTMENT_NAME, department.getDepartmentName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(create, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int update(Department department) {
        LOGGER.debug("update(department:{})", department);

        Map<String, Object> params = new HashMap<>();
        params.put(DEPARTMENT_ID, department.getDepartmentId());
        params.put(DEPARTMENT_NAME, department.getDepartmentName());
        return namedParameterJdbcTemplate.update(update, params);
    }

    @Override
    public int delete(Integer departmentId) {
        LOGGER.debug("delete(departmentId:{})", departmentId);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(DEPARTMENT_ID, departmentId);
        return namedParameterJdbcTemplate.update(delete, mapSqlParameterSource);

    }

    private class DepartmentRowMapper implements RowMapper<Department> {

        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setDepartmentId(resultSet.getInt(COLUMN_DEPARTMENT_ID));
            department.setDepartmentName(resultSet.getString(COLUMN_DEPARTMENT_NAME));
            return department;
        }
    }
}
