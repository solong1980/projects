package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.GradeDefinitionDao;
import io.renren.entity.GradeDefinitionEntity;
import io.renren.service.GradeDefinitionService;

@Service("gradeDefinitionService")
public class GradeDefinitionServiceImpl implements GradeDefinitionService {
	@Autowired
	private GradeDefinitionDao gradeDefinitionDao;

	@Override
	public GradeDefinitionEntity queryObject(Long id) {
		return gradeDefinitionDao.queryObject(id);
	}

	@Override
	public List<GradeDefinitionEntity> queryList(Map<String, Object> map) {
		return gradeDefinitionDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return gradeDefinitionDao.queryTotal(map);
	}

	@Override
	public void save(GradeDefinitionEntity gradeDefinition) {
		gradeDefinitionDao.save(gradeDefinition);
	}

	@Override
	public void update(GradeDefinitionEntity gradeDefinition) {
		gradeDefinitionDao.update(gradeDefinition);
	}

	@Override
	public void delete(Long id) {
		gradeDefinitionDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		gradeDefinitionDao.deleteBatch(ids);
	}

}
