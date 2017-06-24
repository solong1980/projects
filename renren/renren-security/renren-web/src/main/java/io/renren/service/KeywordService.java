package io.renren.service;

import io.renren.entity.KeywordEntity;

import java.util.List;
import java.util.Map;

public interface KeywordService {

	KeywordEntity queryObject(Long id);

	List<KeywordEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void save(KeywordEntity keyword);

	void update(KeywordEntity keyword);

	void delete(Long id);

	void deleteBatch(Long[] ids);
}
