package io.renren.service;

import io.renren.entity.AttachmentsEntity;

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
public interface AttachmentsService {

	AttachmentsEntity queryObject(Long id);

	List<AttachmentsEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void save(AttachmentsEntity attachments);

	void update(AttachmentsEntity attachments);

	void delete(Long id);

	void deleteBatch(Long[] ids);
	
	List<Long> uploadFile(String saveDir,MultipartFile[] files) throws Exception;
}
