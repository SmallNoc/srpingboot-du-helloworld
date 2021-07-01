package com.du.dao;


import com.du.pojo.Department;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//部门Dao
@Repository
public class DepartmentDao {
    //模拟数据空中的数据
    private static Map<Integer, Department> departments= null;

    static{
        departments = new HashMap<>();
        departments.put(101,new Department(101,"教学部门"));
        departments.put(102,new Department(102,"运营部门"));
        departments.put(103,new Department(103,"市场部门"));
        departments.put(104,new Department(104,"后勤部门"));
        departments.put(105,new Department(105,"调研部门"));
    }

    //获得所有部门信息
    public Collection<Department> getDepartment(){
        return departments.values();
    }

    //通过id得到部门
    public Department getDepartmentById(Integer id){
        return departments.get(id);
    }
}
