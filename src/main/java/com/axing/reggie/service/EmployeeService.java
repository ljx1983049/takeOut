package com.axing.reggie.service;

import com.axing.reggie.common.R;
import com.axing.reggie.domain.Employee;
import com.axing.reggie.vo.PageVo;

import java.util.Map;

public interface EmployeeService {
    Employee login(String username);

    R addEmployee(Employee employee);

    PageVo<Employee> pageList(Map<String, Object> map);

    int update(Employee employee);

    Employee getEmployeeById(Long id);
}
