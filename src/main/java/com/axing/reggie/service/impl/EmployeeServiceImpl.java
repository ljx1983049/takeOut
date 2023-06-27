package com.axing.reggie.service.impl;

import com.axing.reggie.common.R;
import com.axing.reggie.domain.Employee;
import com.axing.reggie.mapper.EmployeeMapper;
import com.axing.reggie.service.EmployeeService;
import com.axing.reggie.utils.SnowFlakeIdUtil;
import com.axing.reggie.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(String username) {
        return employeeMapper.login(username);
    }

    @Override
    public R addEmployee(Employee employee) {
        //设置默认密码 123456 ， 并进行MD5加密
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);
        //生成创建和修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //生成默认id。使用雪花算法生成
        long id = SnowFlakeIdUtil.getSnowFlakeId();
        employee.setId(id);
        // 3、Service调用Mapper操作数据库，保存数据
        int count = employeeMapper.addEmployee(employee);
        if (count!=1){
            return R.error("添加失败");
        }
        return R.success("新增员工成功");
    }

    @Override
    public PageVo<Employee> pageList(Map<String, Object> map) {
        //取得总记录条数
        int total = employeeMapper.getTotal(map);
        //取得页面员工列表信息
        List<Employee> records = employeeMapper.getEmployeeListByCondition(map);

        //封装vo
        PageVo<Employee> pageVo = new PageVo<>();
        pageVo.setTotal(total);
        pageVo.setRecords(records);
        return pageVo;
    }

    @Override
    public int update(Employee employee) {
        //设置修改时间
        employee.setUpdateTime(LocalDateTime.now());

        int count = employeeMapper.updateById(employee);
        return count;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeMapper.getEmployeeById(id);
    }
}
