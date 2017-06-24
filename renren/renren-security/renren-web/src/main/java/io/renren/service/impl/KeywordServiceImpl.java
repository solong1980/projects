package io.renren.service.impl;

import io.renren.dao.KeywordDao;
import io.renren.entity.KeywordEntity;
import io.renren.service.KeywordService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("keywordService")
public class KeywordServiceImpl implements KeywordService {
	@Autowired
	private KeywordDao keywordDao;

	@Override
	public KeywordEntity queryObject(Long id) {
		return keywordDao.queryObject(id);
	}

	@Override
	public List<KeywordEntity> queryList(Map<String, Object> map) {
		return keywordDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return keywordDao.queryTotal(map);
	}

	@Override
	public void save(KeywordEntity keyword) {
		keywordDao.save(keyword);
	}

	@Override
	public void update(KeywordEntity keyword) {
		keywordDao.update(keyword);
	}

	@Override
	public void delete(Long id) {
		keywordDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		keywordDao.deleteBatch(ids);
	}

}
