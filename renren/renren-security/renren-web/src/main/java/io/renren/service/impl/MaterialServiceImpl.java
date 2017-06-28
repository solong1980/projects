package io.renren.service.impl;

import io.renren.dao.MaterialDao;
import io.renren.entity.AttachmentEntity;
import io.renren.entity.MaterialEntity;
import io.renren.service.AttachmentService;
import io.renren.service.MaterialService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("materialService")
public class MaterialServiceImpl implements MaterialService {
	@Autowired
	private MaterialDao materialDao;
	@Autowired
	private AttachmentService attachmentService;

	@Override
	public MaterialEntity queryObject(Long id) {
		MaterialEntity materialEntity = materialDao.queryObject(id);
		List<AttachmentEntity> attachments = attachmentService.getAttachmentsByMaterialId(id);
		if (attachments != null && attachments.size() > 0)
			materialEntity.setAttachments(attachments.toArray(new AttachmentEntity[attachments.size()]));
		return materialEntity;
	}

	@Override
	public List<MaterialEntity> queryList(Map<String, Object> map) {
		return materialDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return materialDao.queryTotal(map);
	}

	@Override
	public void save(MaterialEntity material) {
		materialDao.save(material);
	}

	@Override
	public void update(MaterialEntity material) {
		materialDao.update(material);
	}

	@Override
	public void delete(Long id) {
		materialDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		materialDao.deleteBatch(ids);
	}

	@Override
	@Transactional
	public void saveAndWriteBackFileId(MaterialEntity material) {
		AttachmentEntity[] attachments = material.getAttachments();
		if (attachments == null) {
			material.setFileCount(0);
		} else {
			material.setFileCount(attachments.length);
		}

		save(material);
		Long id = material.getId();
		if (attachments != null) {
			// 回写素材id
			for (AttachmentEntity attachmentsEntity : attachments) {
				attachmentsEntity.setMaterialId(id);
				attachmentService.update(attachmentsEntity);
			}
		}
	}

	@Override
	@Transactional
	public void updateAndWriteBackFileId(MaterialEntity material) {
		AttachmentEntity[] attachments = material.getAttachments();
		if (attachments == null) {
			material.setFileCount(0);
		} else {
			material.setFileCount(attachments.length);
		}

		update(material);
		Long id = material.getId();
		if (attachments != null) {
			// 回写素材id
			for (AttachmentEntity attachmentsEntity : attachments) {
				attachmentsEntity.setMaterialId(id);
				attachmentService.update(attachmentsEntity);
			}
		}
	}
}
