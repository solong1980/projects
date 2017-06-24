package io.renren.service;

import io.renren.entity.AttachmentEntity;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 素材文件表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-23 19:10:33
 */
public interface AttachmentService {

	AttachmentEntity queryObject(Long id);

	List<AttachmentEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void save(AttachmentEntity attachment);

	void update(AttachmentEntity attachment);

	void delete(Long id);

	void deleteBatch(Long[] ids);
	
	List<Long> uploadFile(String saveDir,MultipartFile[] files) throws Exception;
}
