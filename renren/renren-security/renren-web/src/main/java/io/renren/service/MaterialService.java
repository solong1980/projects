package io.renren.service;

import io.renren.entity.MaterialEntity;

import java.util.List;
import java.util.Map;

/**
 * 素材表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-22 20:24:04
 */
public interface MaterialService {
	
	MaterialEntity queryObject(Long id);
	
	List<MaterialEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MaterialEntity material);
	
	void saveAndWriteBackFileId(MaterialEntity material);
	
	void update(MaterialEntity materials);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
