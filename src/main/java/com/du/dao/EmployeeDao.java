package com.du.dao;

import com.du.pojo.Department;
import com.du.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//员工数据
@Repository
public class EmployeeDao {
    //模拟数据空中的数据
    private static Map<Integer, Employee> employees= null;
    //员工所属部门
    @Autowired
    private DepartmentDao departmentDao;
    static{
        employees = new HashMap<>();
        employees.put(101,new Employee(1000,"A","1DYD@QQ.COM",1,new Department(101,"教学部门")));
        employees.put(102,new Employee(1001,"B","2DYD@QQ.COM",0,new Department(102,"运营部门")));
        employees.put(103,new Employee(1002,"C","3DYD@QQ.COM",0,new Department(103,"市场部门")));
        employees.put(104,new Employee(1003,"D","4DYD@QQ.COM",0,new Department(104,"后勤部门")));
        employees.put(105,new Employee(1004,"E","5DYD@QQ.COM",1,new Department(105,"调研部门")));
    }


    //主键自增
    private static Integer initId = 1006;

    public void save(Employee employee){
        if(employee.getId() == null){
            employee.setId(initId++);
        }
        employee.setDepartment(departmentDao.getDepartmentById(employee.getDepartment().getId()));
        employees.put(employee.getId(),employee);
    }

    public Collection<Employee> getAll(){
        return  employees.values();
    }

    public Employee getEmployeeByid(Integer id){
        return  employees.get(id);
    }

    public void DeleteEmployeeByid(Integer id){
        employees.remove(id);
    }
}
