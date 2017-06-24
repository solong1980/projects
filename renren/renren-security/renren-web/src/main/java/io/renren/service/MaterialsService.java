package io.renren.service;

import io.renren.entity.MaterialsEntity;

import java.util.List;
import java.util.Map;

/**
 * 素材表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-22 20:24:04
 */
public interface MaterialsService {
	
	MaterialsEntity queryObject(Long id);
	
	List<MaterialsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MaterialsEntity materials);
	
	void saveAndWriteBackFileId(MaterialsEntity materials);
	
	void update(MaterialsEntity materials);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
