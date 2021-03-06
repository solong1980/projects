package com.yz.boster.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yz.boster.commons.utils.BeanUtils;
import com.yz.boster.commons.utils.PageInfo;
import com.yz.boster.commons.utils.StringUtils;
import com.yz.boster.mapper.UserMapper;
import com.yz.boster.mapper.UserRoleMapper;
import com.yz.boster.model.User;
import com.yz.boster.model.UserRole;
import com.yz.boster.model.vo.UserVo;
import com.yz.boster.service.IUserService;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements
		IUserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public User selectOneByLoginName(String userName) {
		EntityWrapper<User> wrapper = new EntityWrapper<User>(new User());
		if (null != userName) {
			wrapper.where("login_name = {0}", userName);
		} else {
			return null;
		}
		return this.selectOne(wrapper);
	}

	@Override
	public List<User> selectByLoginName(UserVo userVo) {
		User user = new User();
		user.setLoginName(userVo.getLoginName());
		EntityWrapper<User> wrapper = new EntityWrapper<User>(user);
		if (null != userVo.getLoginName()) {
			wrapper.where("login_name like concat('%',{0},'%')", userVo.getLoginName());
		}
		return this.selectList(wrapper);
	}

	@Override
	public void insertByVo(UserVo userVo) {
		User user = BeanUtils.copy(userVo, User.class);
		user.setCreateTime(new Date());
		this.insert(user);

		Long id = user.getId();
		String[] roles = userVo.getRoleIds().split(",");
		UserRole userRole = new UserRole();
		for (String string : roles) {
			userRole.setUserId(id);
			userRole.setRoleId(Long.valueOf(string));
			userRoleMapper.insert(userRole);
		}
	}

	@Override
	public UserVo selectVoById(Long id) {
		return userMapper.selectUserVoById(id);
	}

	@Override
	public void updateByVo(UserVo userVo) {
		User user = BeanUtils.copy(userVo, User.class);
		if (StringUtils.isBlank(user.getPassword())) {
			user.setPassword(null);
		}
		this.updateById(user);

		Long id = userVo.getId();
		List<UserRole> userRoles = userRoleMapper.selectByUserId(id);
		if (userRoles != null && !userRoles.isEmpty()) {
			for (UserRole userRole : userRoles) {
				userRoleMapper.deleteById(userRole.getId());
			}
		}

		String[] roles = userVo.getRoleIds().split(",");
		UserRole userRole = new UserRole();
		for (String string : roles) {
			userRole.setUserId(id);
			userRole.setRoleId(Long.valueOf(string));
			userRoleMapper.insert(userRole);
		}
	}

	@Override
	public void updatePwdByUserId(Long userId, String md5Hex) {
		User user = new User();
		user.setId(userId);
		user.setPassword(md5Hex);
		this.updateById(user);
	}

	@Override
	public void selectDataGrid(PageInfo pageInfo) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(
				pageInfo.getNowpage(), pageInfo.getSize());
		page.setOrderByField(pageInfo.getSort());
		page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
		List<Map<String, Object>> list = userMapper.selectUserPage(page,
				pageInfo.getCondition());
		pageInfo.setRows(list);
		pageInfo.setTotal(page.getTotal());
	}

	@Override
	public void deleteUserById(Long id) {
		this.deleteById(id);
		userRoleMapper.deleteByUserId(id);
	}

	@Override
	public void insert(UserVo userVo) {
		User user = BeanUtils.copy(userVo, User.class);
		user.setCreateTime(new Date());
		this.insert(user);
	}

}