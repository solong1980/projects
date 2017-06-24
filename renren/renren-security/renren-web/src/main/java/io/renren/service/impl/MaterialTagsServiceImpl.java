package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.MaterialTagsDao;
import io.renren.entity.MaterialTagsEntity;
import io.renren.service.MaterialTagsService;

@Service("materialTagsService")
public class MaterialTagsServiceImpl implements MaterialTagsService {
	@Autowired
	private MaterialTagsDao materialTagsDao;

	@Override
	public MaterialTagsEntity queryObject(Long id) {
		return materialTagsDao.queryObject(id);
	}

	@Override
	public List<MaterialTagsEntity> queryList(Map<String, Object> map) {
		return materialTagsDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return materialTagsDao.queryTotal(map);
	}

	@Override
	public void save(MaterialTagsEntity materialTags) {
		materialTagsDao.save(materialTags);
	}

	@Override
	public void update(MaterialTagsEntity materialTags) {
		materialTagsDao.update(materialTags);
	}

	@Override
	public void delete(Long id) {
		materialTagsDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		materialTagsDao.deleteBatch(ids);
	}

}
