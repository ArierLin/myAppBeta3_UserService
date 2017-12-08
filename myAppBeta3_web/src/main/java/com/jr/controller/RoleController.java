package com.jr.controller;

import com.jr.dubboInterface.UserDubboInterface;
import com.jr.entity.Authority;
import com.jr.entity.Role;
import com.jr.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@SuppressWarnings(value="all")
@Controller
@RequestMapping(value="/role")
public class RoleController {

	private static Logger log = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private UserDubboInterface userDubboInterface;

//	@Autowired
//	private userDubboInterface userDubboInterface;
//
//	@Autowired
//	private userDubboInterface userDubboInterface;
	
	@RequestMapping(value="toRoleManagementPage")
	public String toRoleManagementPage(ModelMap map){
		List<Role> roles = userDubboInterface.findAllRoles();
		map.put("roles", roles);
		return "roleList";
	}
	
	/**
	 * 跳转到角色添加页面
	 * @author jql
	 * @return
	 */
	@RequestMapping(value="toAddRolePage")
	public String toAddRolePage(ModelMap map){
		log.debug("跳转到角色添加页面");
		List<Authority> authorities = userDubboInterface.findAllAuthorities();
		map.put("authorities", authorities);
		return "roleAddOrUpdate";
	}
	
	/**
	 * 保存一个角色
	 * @author jql
	 * @param name
	 * @param type
	 * @param desc
	 * @param idStr
	 * @return
	 */
	@RequestMapping(value="saveRole")
	@ResponseBody
	public boolean saveRole(@RequestParam(value="name")String name,
			@RequestParam(value="type")int type,
			@RequestParam(value="desc")String desc,
			@RequestParam(value="idStr",required=false)String idStr){
		log.debug("保存一个角色。name:"+name+",type:"+type+",desc:"+desc+",权限id:"+idStr);
		boolean result = false;
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Role role = new Role();
		role.setName(name);
		role.setType(type);
		role.setDesc(desc);
		role.setCreateTime(sdf.format(new Date()));
		int i = userDubboInterface.saveRoleWithAuthority(role, idStr);
		if (i==Constants.SysConstants.SUCCESS) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 跳转到编辑角色信息页面
	 * @author jql
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="toUpdateOneRolePage")
	public String toUpdateOneRolePage(int roleId,ModelMap map){
		log.debug("跳转到编辑角色信息页面。roleId:"+roleId);
		Role role = userDubboInterface.queryRoleJoinAuthority(roleId);
		List<Authority> selectedAuthorities = role.getAuthority();
		List<Authority> authorities = userDubboInterface.findAllAuthorities();
		
		map.put("authorities", authorities);
		map.put("role", role);
		map.put("selectedAuthorities", selectedAuthorities);
		
		return "roleAddOrUpdate";
	}
	
	/**
	 * 修改一个角色。
	 * @author jql
	 * @param name
	 * @param type
	 * @param desc
	 * @param idStr
	 * @return
	 */
	@RequestMapping(value="updateRole")
	@ResponseBody
	public boolean updateRole(@RequestParam(value="id")int id,@RequestParam(value="name")String name,
			@RequestParam(value="type")int type,
			@RequestParam(value="desc")String desc,
			@RequestParam(value="idStr",required=false)String idStr){
		log.debug("修改一个角色。角色id:"+id+",name:"+name+",type:"+type+",desc:"+desc+",权限id:"+idStr);
		boolean result = false;
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Role role = new Role();
		role.setId(id);
		role.setName(name);
		role.setType(type);
		role.setDesc(desc);
		int i = userDubboInterface.updateRoleAuthorityRelation(role, idStr);
		if (i==Constants.SysConstants.SUCCESS) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 删除角色信息
	 * @author jql
	 * @param idStr
	 * @return
	 */
	@RequestMapping(value="multiDeleteRole")
	@ResponseBody
	public String multiDeleteRole(@RequestParam(value="idStr")String idStr){
		log.debug("删除角色信息，待删除的角色id："+idStr);
		int result = userDubboInterface.multiDeleteRole(idStr);
		if (result == Constants.SysConstants.SUCCESS) {
			return "success";
		}else if (result == Constants.SysConstants.ROLE_EXIST_USER_FAIL) {
			return "角色下存在用户，删除失败！";
		}else if (result == Constants.SysConstants.ROLE_EXIST_USER_SUCCESS) {
			return "部分角色下存在用户。已删除不存在用户的角色。";
		}
		return "角色删除失败！";
	}
	
	
	
}
