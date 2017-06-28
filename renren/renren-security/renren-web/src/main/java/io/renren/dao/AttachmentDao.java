package io.renren.dao;

import java.util.List;

import io.renren.entity.AttachmentEntity;

/**
 * 素材文件表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-23 19:10:33
 */
public interface AttachmentDao extends BaseDao<AttachmentEntity> {
	/**
	 * 根据素材id查询素材文件
	 * @param materialId
	 * @return
	 */
	List<AttachmentEntity> getAttachmentsByMaterialId(Long materialId);
	/**
	 * 更新附件素材id为空,脱离附件同素材关系
	 * @param attachmentId
	 */
	void updateMaterialIdNull(Long attachmentId);	
}
