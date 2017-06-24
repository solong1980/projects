package io.renren.service.impl;

import io.renren.dao.KeywordsDao;
import io.renren.entity.KeywordsEntity;
import io.renren.service.KeywordsService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("keywordsService")
public class KeywordsServiceImpl implements KeywordsService {
	@Autowired
	private KeywordsDao keywordsDao;

	@Override
	public KeywordsEntity queryObject(Long id) {
		return keywordsDao.queryObject(id);
	}

	@Override
	public List<KeywordsEntity> queryList(Map<String, Object> map) {
		return keywordsDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return keywordsDao.queryTotal(map);
	}

	@Override
	public void save(KeywordsEntity keywords) {
		keywordsDao.save(keywords);
	}

	@Override
	public void update(KeywordsEntity keywords) {
		keywordsDao.update(keywords);
	}

	@Override
	public void delete(Long id) {
		keywordsDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		keywordsDao.deleteBatch(ids);
	}

}
