import com.epam.brest.courses.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentDtoService {

    /**
     * Find all departments with avg salary by department.
     *
     * @return departments list.
     */
    List<DepartmentDto> findAllWithAvgSalary();
}
