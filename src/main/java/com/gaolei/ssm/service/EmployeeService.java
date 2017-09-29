package com.gaolei.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaolei.ssm.bean.Employee;
import com.gaolei.ssm.bean.EmployeeExample;
import com.gaolei.ssm.bean.EmployeeExample.Criteria;
import com.gaolei.ssm.dao.EmployeeMapper;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeMapper employeeMapper;
	
	public List getAll(){
		EmployeeExample ee=new EmployeeExample();
		ee.setOrderByClause("emp_id");
		return employeeMapper.selectByExampleWithDept(ee); //查询所有
	}
	public void saveEmp(Employee employee){
		employeeMapper.insertSelective(employee);
	}
	public boolean checkUser(String user) {
		EmployeeExample example=new EmployeeExample();
		Criteria cri=example.createCriteria();
		cri.andEmpNameEqualTo(user);
		int count=(int) employeeMapper.countByExample(example);
		return count==0;
	}
	public Employee getEmp(Integer id) {
		return employeeMapper.selectByPrimaryKey(id);
	}
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}
	/*
	 * 批量删除
	 */
	public void bathDeleteEmps(List<Integer> list) {
		EmployeeExample example=new EmployeeExample();
		Criteria criteria=example.createCriteria();
		criteria.andEmpIdIn(list);
		employeeMapper.deleteByExample(example);
	}
}
