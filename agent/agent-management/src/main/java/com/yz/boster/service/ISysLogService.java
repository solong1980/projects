package com.yz.boster.service;

import com.baomidou.mybatisplus.service.IService;
import com.yz.boster.commons.utils.PageInfo;
import com.yz.boster.model.SysLog;

/**
 *
 * SysLog 表数据服务层接口
 *
 */
public interface ISysLogService extends IService<SysLog> {

    void selectDataGrid(PageInfo pageInfo);

}