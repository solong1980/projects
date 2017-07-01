package io.renren.service;

import io.renren.entity.AttachmentEntity;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 素材文件表
 * 
 * @author longlianghua
 * @email solong1980@163.com
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

	List<AttachmentEntity> uploadFile(String saveDir, MultipartFile[] files) throws Exception;

	List<AttachmentEntity> getAttachmentsByMaterialId(Long materialId);
	
	/**
	 * 删除附件同素材关系,即设置附件material_id为空即可
	 * @param attachmentId
	 */
	void delFromMaterial(Long attachmentId);

	void updateMaterialId(AttachmentEntity attachment);
}
