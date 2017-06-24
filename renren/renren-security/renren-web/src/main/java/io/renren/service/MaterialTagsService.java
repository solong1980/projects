package io.renren.service;

import io.renren.entity.MaterialTagsEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-24 11:19:40
 */
public interface MaterialTagsService {
	
	MaterialTagsEntity queryObject(Long id);
	
	List<MaterialTagsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MaterialTagsEntity materialTags);
	
	void update(MaterialTagsEntity materialTags);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
