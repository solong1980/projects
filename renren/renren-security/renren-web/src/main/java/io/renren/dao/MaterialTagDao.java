package io.renren.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import io.renren.entity.MaterialTagEntity;

/**
 * 
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-24 11:19:40
 */
public interface MaterialTagDao extends BaseDao<MaterialTagEntity> {
	List<MaterialTagEntity> doSearch(Map<String, Object> map);

	List<MaterialTagEntity> queryByIds(@Param("list") Long[] ids);
}
