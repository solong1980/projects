package com.yz.boster.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.yz.boster.commons.result.Tree;
import com.yz.boster.commons.shiro.ShiroUser;
import com.yz.boster.model.Resource;

/**
 *
 * Resource 表数据服务层接口
 *
 */
public interface IResourceService extends IService<Resource> {

    List<Resource> selectAll();

    List<Tree> selectAllMenu();

    List<Tree> selectAllTree();

    List<Tree> selectTree(ShiroUser shiroUser);

}