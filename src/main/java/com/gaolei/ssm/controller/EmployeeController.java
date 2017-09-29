package com.gaolei.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaolei.ssm.bean.Employee;
import com.gaolei.ssm.bean.Msg;
import com.gaolei.ssm.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	
//	@RequestMapping("/emps")
//	public String getEmps(@RequestParam(value="pn",defaultValue="1")Integer pn,Model model){
//		//分页查询
//		int pageSize=5;
//		PageHelper.startPage(pn, pageSize);
//		List<Employee> emps=employeeService.getAll();
//		PageInfo<Employee> page=new PageInfo<>(emps,5);
//		model.addAttribute("pageInfo",page);
//		return "list";
//	}
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1")Integer pn){
		int pageSize=5;
		PageHelper.startPage(pn, pageSize);
		List<Employee> emps=employeeService.getAll();
		PageInfo<Employee> pageInfo=new PageInfo<>(emps,5);
		return Msg.success().add("pageInfo", pageInfo);
	}
	@RequestMapping(value="/emps",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmps(@Validated Employee employee,BindingResult result){//从前端传来的参数springmvc会自动封装
		//System.out.println(employee);
		if(result.hasErrors()){
			Map<String, String>map=new HashMap<>();
			List<FieldError> list=result.getFieldErrors();
			for (FieldError fieldError : list) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		}else{
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}
	/*
	 * 后端校验
	 */
	@ResponseBody
	@RequestMapping("/checkUser")
	public Msg checkUser(@RequestParam("empName")String empName){
		String regx="^[a-zA-Z0-9_-]{6,16}$|(^[\u2E80-\u9FFF]{2,5}$)";
		if(!empName.matches(regx)){
			return Msg.fail().add("error", "用户名格式不正确！");
		}
		boolean flag=employeeService.checkUser(empName);
		if(flag){
			return Msg.success();
		}else{
			return Msg.fail().add("error", "用户名重复不可用！");
		}
	}
	@RequestMapping(value="/emp/{id}")
	@ResponseBody
	public Msg getEmp(@PathVariable("id")Integer id){
		Employee employee=employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	@ResponseBody
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	public Msg updateEmp(Employee employee){
		//System.out.println(employee);
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("ids")String ids){
		if(ids.contains("-")){
			List<Integer> list=new ArrayList<>();
			String[]strs=ids.split("-");
			for (String id : strs) {
				list.add(Integer.parseInt(id));
			}
			employeeService.bathDeleteEmps(list);
		}else{
			employeeService.deleteEmp(Integer.parseInt(ids));
		}
		return Msg.success();
	}
}
