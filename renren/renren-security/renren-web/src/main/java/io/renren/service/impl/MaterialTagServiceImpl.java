package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.MaterialTagDao;
import io.renren.entity.MaterialTagEntity;
import io.renren.service.MaterialTagService;

@Service("materialTagService")
public class MaterialTagServiceImpl implements MaterialTagService {
	@Autowired
	private MaterialTagDao materialTagDao;

	@Override
	public MaterialTagEntity queryObject(Long id) {
		return materialTagDao.queryObject(id);
	}

	@Override
	public List<MaterialTagEntity> queryList(Map<String, Object> map) {
		return materialTagDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return materialTagDao.queryTotal(map);
	}

	@Override
	public void save(MaterialTagEntity materialTag) {
		materialTagDao.save(materialTag);
	}

	@Override
	public void update(MaterialTagEntity materialTag) {
		materialTagDao.update(materialTag);
	}

	@Override
	public void delete(Long id) {
		materialTagDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		materialTagDao.deleteBatch(ids);
	}

}
