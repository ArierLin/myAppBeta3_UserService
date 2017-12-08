package com.jr.dubboInterfaceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jr.dubboInterface.UserDubboInterface;
import com.jr.entity.Authority;
import com.jr.entity.Role;
import com.jr.entity.RoleAuthorityRelation;
import com.jr.entity.User;
import com.jr.entity.UserRoleRelation;
import com.jr.service.AuthorityService;
import com.jr.service.RoleService;
import com.jr.service.UserService;


@Service("userDubboInterface")
public class UserDubboInterfaceImpl implements UserDubboInterface {
	
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;

	@Override
	public Authority getAuthorityById(int id) {
		return authorityService.getAuthorityById(id);
	}

	@Override
	public Authority getAuthorityByName(String name) {
		return authorityService.getAuthorityByName(name);
	}

	@Override
	public int saveAuthority(Authority authority) {
		return authorityService.saveAuthority(authority);
	}

	@Override
	public int deleteAuthority(Authority authority) {
		return authorityService.deleteAuthority(authority);
	}

	@Override
	public List<Authority> findAllAuthorities() {
		return authorityService.findAllAuthorities();
	}

	@Override
	public List<Authority> findAllAuthoritiesByRoleId(int roleId) {
		return authorityService.findAllAuthoritiesByRoleId(roleId);
	}

	@Override
	public List<Authority> findAllAuthoritiesByUserName(String userName) {
		return authorityService.findAllAuthoritiesByUserName(userName);
	}

	@Override
	public Role getOneRoleById(int id) {
		return roleService.getOneRoleById(id);
	}

	@Override
	public Role queryRoleJoinAuthority(int id) {
		return roleService.queryRoleJoinAuthority(id);
	}

	@Override
	public int saveRole(Role role) {
		return roleService.saveRole(role);
	}

	@Override
	public int saveRoleAuthorityRelation(RoleAuthorityRelation roleAuthorityRelation) {
		return roleService.saveRoleAuthorityRelation(roleAuthorityRelation);
	}

	@Override
	public List<Role> findAllRoles() {
		return roleService.findAllRoles();
	}

	@Override
	public int saveRoleWithAuthority(Role role, String authorityIds) {
		return roleService.saveRoleWithAuthority(role, authorityIds);
	}

	@Override
	public int multiDeleteRole(String idStr) {
		return roleService.multiDeleteRole(idStr);
	}

	@Override
	public int updateRole(Role newRole) {
		return roleService.updateRole(newRole);
	}

	@Override
	public int updateRoleAuthorityRelation(Role role, String authorityIds) {
		return roleService.updateRoleAuthorityRelation(role, authorityIds);
	}

	@Override
	public int saveUser(User user) {
		return userService.saveUser(user);
	}

	@Override
	public int saveUserRoleRelation(UserRoleRelation userRoleRelation) {
		return userService.saveUserRoleRelation(userRoleRelation);
	}

	@Override
	public User getOneUserByName(String name) {
		return userService.getOneUserByName(name);
	}

	@Override
	public User getOneUserById(int id) {
		return userService.getOneUserById(id);
	}

	@Override
	public User queryUserJoinRole(int id) {
		return userService.queryUserJoinRole(id);
	}

	@Override
	public List<User> findAllUsers() {
		return userService.findAllUsers();
	}

	@Override
	public List<String> getAuthoritiesByUserName(String userName) {
		return userService.getAuthoritiesByUserName(userName);
	}

	@Override
	public List<String> getRolesByUserName(String userName) {
		return userService.getRolesByUserName(userName);
	}

	@Override
	public List<User> findAllUserByRoleId(int roleId) {
		return userService.findAllUserByRoleId(roleId);
	}

	@Override
	public boolean validateUserName(String userName) {
		return userService.validateUserName(userName);
	}

	@Override
	public int saveUserWithRole(User user, String roleIds) {
		return userService.saveUserWithRole(user, roleIds);
	}

	@Override
	public int updateUserRoleRelation(User newUser, String roleIds) {
		return userService.updateUserRoleRelation(newUser, roleIds);
	}

	@Override
	public int multiDeleteUser(String idStr) {
		return userService.multiDeleteUser(idStr);
	}

}
