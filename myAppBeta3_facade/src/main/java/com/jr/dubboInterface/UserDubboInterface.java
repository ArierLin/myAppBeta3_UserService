package com.jr.dubboInterface;

import java.util.List;

import com.jr.entity.Authority;
import com.jr.entity.Role;
import com.jr.entity.RoleAuthorityRelation;
import com.jr.entity.User;
import com.jr.entity.UserRoleRelation;

/**
 * 
* @ClassName: UserDubboInterface
* @Description: TODO()
* @author jr
* @date 2017年11月27日 下午2:31:12
*
 */
public interface UserDubboInterface {
	
	/**
	 * 通过权限id查询一个权限
	 * @author jql
	 * @param id
	 * @return
	 */
	public Authority getAuthorityById(int id);
	
	/**
	 * 通过权限名称查询一个权限
	 * @author jql
	 * @param name
	 * @return
	 */
	public Authority getAuthorityByName(String name);
	
	/**
	 * 保存一个权限
	 * @author jql
	 * @param authority
	 * @return
	 */
	public int saveAuthority(Authority authority);
	
	/**
	 * 删除一个权限
	 * @author jql
	 * @param authority
	 * @return
	 */
	public int deleteAuthority(Authority authority);

	/**
	 * 查询所有权限
	 * @author jql
	 * @return
	 */
	public List<Authority> findAllAuthorities();
	
	/**
	 * 通过角色id查找角色的权限
	 * @author jql
	 * @param roleId
	 * @return
	 */
	public List<Authority> findAllAuthoritiesByRoleId(int roleId);
	
	/**
	 * 通过用户名查询用户权限
	 * @author jql
	 * @param userName
	 * @return
	 */
	public List<Authority> findAllAuthoritiesByUserName(String userName);
	public Role getOneRoleById(int id);
	
	//根据角色id查询角色和权限信息
	public Role queryRoleJoinAuthority(int id);
	
	public int saveRole(Role role);
	
	public int saveRoleAuthorityRelation(RoleAuthorityRelation roleAuthorityRelation);
	
	public List<Role> findAllRoles();
	
	/**
	 * 保存角色和权限
	 * @author jql
	 * @param role
	 * @param authorityIds
	 * @return
	 */
	public int saveRoleWithAuthority(Role role, String authorityIds);
	
	/**
	 * 批量删除角色
	 * @author jql
	 * @param idStr
	 * @return
	 */
	public int multiDeleteRole(String idStr);
	
	/**
	 * 更新角色
	 * @author jql
	 * @param newRole
	 * @return
	 */
	public int updateRole(Role newRole);
	
	/**
	 * 更新角色和权限
	 * @author jql
	 * @return
	 */
	public int updateRoleAuthorityRelation(Role role, String authorityIds);
	public int saveUser(User user);

	public int saveUserRoleRelation(UserRoleRelation userRoleRelation);
	
	public User getOneUserByName(String name);
	
	public User getOneUserById(int id);
	
	public User queryUserJoinRole(int id);
	
	public List<User> findAllUsers();
	
	/**
	 * 根据用户名查询用户具有的权限
	 * @author jql
	 * @param userName
	 * @return
	 */
	public List<String> getAuthoritiesByUserName(String userName);
	
	/**
	 * 根据用户名查询用户具有的角色
	 * @author jql
	 * @param userName
	 * @return
	 */
	public List<String> getRolesByUserName(String userName);
	
	
	/**
	 * 根据角色id查询该角色下的所有用户
	 * @author jql
	 * @param roleId
	 * @return
	 */
	public List<User> findAllUserByRoleId(int roleId);
	
	/**
	 * 判断用户名是否可以使用
	 * @author jql
	 * @param userName
	 * @return
	 */
	public boolean validateUserName(String userName);
	
	/**
	 * 保存用户和角色
	 * @author jql
	 * @param user
	 * @param roleIds
	 * @return
	 */
	public int saveUserWithRole(User user, String roleIds);

	/**
	 * 更新用户、用户角色关联信息
	 * @author jql
	 * @param newUser
	 * @param roleIds
	 */
	public int updateUserRoleRelation(User newUser, String roleIds);

	/**
	 * 批量删除用户
	 * @author jql
	 * @param idStr
	 * @return
	 */
	public int multiDeleteUser(String idStr);
	
	

}
