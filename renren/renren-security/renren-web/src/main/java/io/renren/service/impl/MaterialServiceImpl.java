package io.renren.service.impl;

import io.renren.dao.MaterialDao;
import io.renren.entity.AttachmentEntity;
import io.renren.entity.MaterialEntity;
import io.renren.entity.MaterialPriceEntity;
import io.renren.service.AttachmentService;
import io.renren.service.MaterialPriceService;
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

	@Autowired
	private MaterialPriceService materialPriceService;

	@Override
	public MaterialEntity queryObject(Long id) {
		MaterialEntity materialEntity = materialDao.queryObject(id);
		if (materialEntity != null) {
			List<AttachmentEntity> attachments = attachmentService.getAttachmentsByMaterialId(id);
			if (attachments != null && attachments.size() > 0)
				materialEntity.setAttachments(attachments.toArray(new AttachmentEntity[attachments.size()]));

			// 查询标签

			// 查询定价
			List<MaterialPriceEntity> materialPrices = materialPriceService.getMaterialPriceSettings(id);
			materialEntity.setMaterialPrices(materialPrices);

		}
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
	@Transactional
	public void save(MaterialEntity material) {
		materialDao.save(material);
		Long materialId = material.getId();
		List<MaterialPriceEntity> materialPrices = material.getMaterialPrices();
		if (materialPrices != null && materialPrices.isEmpty()) {
			for (MaterialPriceEntity materialPrice : materialPrices) {
				materialPrice.setMaterialId(materialId);
				materialPriceService.save(materialPrice);
			}
		}
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
		Long materialId = material.getId();
		if (attachments != null) {
			// 回写素材id
			for (AttachmentEntity attachmentsEntity : attachments) {
				attachmentsEntity.setMaterialId(materialId);
				attachmentService.update(attachmentsEntity);//待修改
			}
		}
		// 新增素材价格设置
		List<MaterialPriceEntity> materialPrices = material.getMaterialPrices();
		if (materialPrices != null && !materialPrices.isEmpty()) {
			for (MaterialPriceEntity materialPrice : materialPrices) {
				materialPrice.setMaterialId(materialId);
				materialPriceService.save(materialPrice);
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
		Long materialId = material.getId();
		if (attachments != null) {
			// 回写素材id
			for (AttachmentEntity attachmentsEntity : attachments) {
				attachmentsEntity.setMaterialId(materialId);
				attachmentService.update(attachmentsEntity);
			}
		}
		materialPriceService
				.updateMaterialPriceSettings(materialId, material.getStatus(), material.getMaterialPrices());
	}
}
