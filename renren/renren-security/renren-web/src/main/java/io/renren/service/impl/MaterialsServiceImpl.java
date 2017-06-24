package io.renren.service.impl;

import io.renren.dao.MaterialsDao;
import io.renren.entity.AttachmentsEntity;
import io.renren.entity.MaterialsEntity;
import io.renren.service.AttachmentsService;
import io.renren.service.MaterialsService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("materialsService")
public class MaterialsServiceImpl implements MaterialsService {
	@Autowired
	private MaterialsDao materialsDao;
	@Autowired
	private AttachmentsService attachmentService;

	@Override
	public MaterialsEntity queryObject(Long id) {
		return materialsDao.queryObject(id);
	}

	@Override
	public List<MaterialsEntity> queryList(Map<String, Object> map) {
		return materialsDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return materialsDao.queryTotal(map);
	}

	@Override
	public void save(MaterialsEntity materials) {
		materialsDao.save(materials);
	}

	@Override
	public void update(MaterialsEntity materials) {
		materialsDao.update(materials);
	}

	@Override
	public void delete(Long id) {
		materialsDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		materialsDao.deleteBatch(ids);
	}

	@Override
	@Transactional
	public void saveAndWriteBackFileId(MaterialsEntity materials) {
		AttachmentsEntity[] attachments = materials.getAttachments();
		if (attachments == null) {
			materials.setFileCount(0);
		} else {
			materials.setFileCount(attachments.length);
		}

		save(materials);
		Long id = materials.getId();
		if (attachments != null) {
			// 回写素材id
			for (AttachmentsEntity attachmentsEntity : attachments) {
				attachmentsEntity.setMaterialId(id);
				attachmentService.update(attachmentsEntity);
			}
		}
	}

}
