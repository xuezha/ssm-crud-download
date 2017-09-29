package com.gaolei.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaolei.ssm.bean.Msg;
import com.gaolei.ssm.service.DepartmentService;

@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService; 
	
	@RequestMapping("/depts")
	@ResponseBody
	public Msg getDeptsWithJson(){
		List list=departmentService.getAll();
		//System.out.println(list.size());
		return Msg.success().add("depts", list);
	}
	
}
