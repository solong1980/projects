package io.renren.service.impl;

import io.renren.dao.SysUserRoleDao;
import io.renren.service.SysUserRoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {
	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	@Override
	@CacheEvict(cacheNames = "io.renren.shiro.UserRealm.authorizationCache", key = "#userId")
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		if (roleIdList.size() == 0) {
			return;
		}

		// 先删除用户与角色关系
		sysUserRoleDao.delete(userId);

		// 保存用户与角色关系
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("roleIdList", roleIdList);
		sysUserRoleDao.save(map);
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return sysUserRoleDao.queryRoleIdList(userId);
	}

	@Override
	@CacheEvict(cacheNames = "io.renren.shiro.UserRealm.authorizationCache", key = "#userId")
	public void delete(Long userId) {
		sysUserRoleDao.delete(userId);
	}
}
