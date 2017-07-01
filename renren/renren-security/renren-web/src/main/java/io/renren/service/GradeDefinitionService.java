package io.renren.service;

import io.renren.entity.GradeDefinitionEntity;

import java.util.List;
import java.util.Map;

/**
 * 会员等级定义表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-30 19:10:33
 */
public interface GradeDefinitionService {
	
	GradeDefinitionEntity queryObject(Long id);
	
	List<GradeDefinitionEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(GradeDefinitionEntity gradeDefinition);
	
	void update(GradeDefinitionEntity gradeDefinition);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
