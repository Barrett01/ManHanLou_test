package com.hh.mhl.service;

import com.hh.mhl.dao.EmployeeDAO;
import com.hh.mhl.domain.Employee;

/*(
该类完成对employee表的各种操作（通过调用EmployeeDAO对象完成）
 */
public class EmployeeService {
    //定义一EmployeeDAO 属性
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public Employee getEmployeeByIdAndPwd(String empId, String pwd) {
        return employeeDAO.querySingle("select * from employee where empId = ? and pwd = md5(?)",
                Employee.class, empId, pwd);
    }
}
