package com.jr.dao;

import java.util.List;

import com.jr.entity.Authority;


public interface AuthorityMapper {
	
	public Authority getAuthorityById(int id);
	
	public Authority getAuthorityByName(String name);
	
	public int saveAuthority(Authority authority);
	
	
	public int deleteAuthority(Authority authority);
	
	public List<Authority> findAllAuthorities();
	
	/**
	 * 通过角色id查找角色的权限
	 * @author jql
	 * @param roleId
	 * @return
	 */
	public List<Authority> findAllAuthoritiesByRoleId(int roleId);
	
}
