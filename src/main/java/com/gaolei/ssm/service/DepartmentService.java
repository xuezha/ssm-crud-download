package com.gaolei.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaolei.ssm.bean.Department;
import com.gaolei.ssm.dao.DepartmentMapper;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentMapper departmentMapper;
	
	public List<Department> getAll(){
		return departmentMapper.selectByExample(null);
	}
}
