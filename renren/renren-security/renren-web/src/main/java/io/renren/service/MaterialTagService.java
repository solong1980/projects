package io.renren.service;

import io.renren.entity.MaterialTagEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-24 11:19:40
 */
public interface MaterialTagService {
	
	MaterialTagEntity queryObject(Long id);
	
	List<MaterialTagEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MaterialTagEntity materialTag);
	
	void update(MaterialTagEntity materialTag);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	List<MaterialTagEntity> doSearch(Map<String, Object> map);
}
