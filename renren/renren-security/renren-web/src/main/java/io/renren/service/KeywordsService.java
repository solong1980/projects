package io.renren.service;

import io.renren.entity.KeywordsEntity;

import java.util.List;
import java.util.Map;

public interface KeywordsService {

	KeywordsEntity queryObject(Long id);

	List<KeywordsEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void save(KeywordsEntity keywords);

	void update(KeywordsEntity keywords);

	void delete(Long id);

	void deleteBatch(Long[] ids);
}
