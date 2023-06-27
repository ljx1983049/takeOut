package com.axing.reggie.web.controller;

import com.axing.reggie.common.R;
import com.axing.reggie.domain.Employee;
import com.axing.reggie.service.EmployeeService;
import com.axing.reggie.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee 员工信息实体类
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        // 1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        Employee emp = employeeService.login(employee.getUsername());

        //3、如果没有查询到则返回登录失败结果
        if (emp==null){
            return R.error("登录失败：用户不存在");
        }

        // 4、密码比对，如果不一致则返回登录失败结果
        if(!password.equals(emp.getPassword())){
            return R.error("登录失败：密码错误");
        }

        // 5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (0==emp.getStatus()){
            return R.error("员工已禁用");
        }

        // 6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employeeId",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        // 1、清理Session中的用户id
        // request.getSession().setAttribute("employeeId",null);
        request.getSession().removeAttribute("employeeId");
        // 2、返回结果
        return R.success("退出成功");
    }

    /**
     * 添加员工
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<Employee> addEmployee(HttpServletRequest request,@RequestBody Employee employee){
        // 1、页面发送ajax请求，将新增员工页面中输入的数据以json的形式提交到服务端
        //获取创建人，并设置创建人
        Long employeeId = (Long) request.getSession().getAttribute("employeeId");
        employee.setCreateUser(employeeId);
        //设置修改人
        employee.setUpdateUser(employeeId);
        // 2、服务端Controller接收页面提交的数据并调用Service将数据进行保存
        // 3、Service调用Mapper操作数据库，保存数据
        R r = employeeService.addEmployee(employee);
        return r;
    }

    /**
     * 查询页面列表员工信息
     * @param page 页码
     * @param pageSize 每页显示条数
     * @param name 条件查询字段
     * @return
     */
    @GetMapping("/page")
    public R<PageVo> page(int page,int pageSize,String name){
        // 1、页面发送ajax请求，将分页查询参数(page、pageSize、name)提交到服务端
        // 2、服务端Controller接收页面提交的数据并调用Service查询数据
        //计算从哪条记录开始查询
        int startNum = (page-1)*pageSize;
        Map<String,Object> map = new HashMap<>();
        map.put("startNum",startNum);
        map.put("pageSize",pageSize);
        map.put("name",name);
        log.info("封装的map参数{}",map);
        // 3、Service调用Mapper操作数据库，查询分页数据
        PageVo<Employee> pageVo = employeeService.pageList(map);
        // 4、Controller将查询到的分页数据响应给页面
        return R.success(pageVo);
        // 5、页面接收到分页数据并通过Elementul的Table组件展示到页面上
    }

    /**
     * 根据id修改员工信息
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        //获取当前操作用户id
        Long employeeId = (Long) request.getSession().getAttribute("employeeId");
        employee.setUpdateUser(employeeId);
        int count = employeeService.update(employee);
        if (count!=1){
            return R.error("修改信息失败");
        }
        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null){
            return R.error("查询员工信息失败");
        }
        return R.success(employee);
    }





















}
