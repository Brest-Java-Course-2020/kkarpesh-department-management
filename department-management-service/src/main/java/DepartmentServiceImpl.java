import com.epam.brest.courses.dao.DepartmentDao;
import com.epam.brest.courses.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentDao departmentDao;

    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        LOGGER.debug("Find all departments");
        return departmentDao.findAll();
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {
        LOGGER.debug("Find department by id = {}", departmentId);
        return departmentDao.findById(departmentId);
    }

    @Override
    public Integer create(Department department) {
        LOGGER.debug("Create new department {}", department);
        return departmentDao.create(department);
    }

    @Override
    public Integer update(Department department) {
        LOGGER.debug("Update department {}", department);
        return departmentDao.update(department);
    }

    @Override
    public Integer delete(Integer departmentId) {
        LOGGER.debug("Delete department with id = {}", departmentId);
        return departmentDao.delete(departmentId);
    }
}
