package com.axing.reggie.mapper;

import com.axing.reggie.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMapper {
    Employee login(String username);

    int addEmployee(Employee employee);

    /**
     * 查询页面总记录条数，根据条件查询
     * @param map
     * @return
     */
    int getTotal(Map<String, Object> map);

    /**
     * 查询页面列表员工信息，根据条件和分页查询
     * @param map
     * @return
     */
    List<Employee> getEmployeeListByCondition(Map<String, Object> map);

    /**
     * 根据id修改员工信息
     * @param employee
     * @return
     */
    int updateById(Employee employee);

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    Employee getEmployeeById(Long id);
}
