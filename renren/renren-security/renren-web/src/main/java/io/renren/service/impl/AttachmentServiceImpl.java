package io.renren.service.impl;

import io.renren.dao.AttachmentDao;
import io.renren.entity.AttachmentEntity;
import io.renren.service.AttachmentService;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {

	private static Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

	@Autowired
	private AttachmentDao attachmentDao;

	@Override
	public AttachmentEntity queryObject(Long id) {
		return attachmentDao.queryObject(id);
	}

	@Override
	public List<AttachmentEntity> queryList(Map<String, Object> map) {
		return attachmentDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return attachmentDao.queryTotal(map);
	}

	@Override
	public void save(AttachmentEntity attachment) {
		attachmentDao.save(attachment);
	}

	@Override
	public void update(AttachmentEntity attachment) {
		attachmentDao.update(attachment);
	}

	@Override
	public void delete(Long id) {
		attachmentDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		attachmentDao.deleteBatch(ids);
	}

	@Override
	public List<AttachmentEntity> uploadFile(String saveDir, MultipartFile[] files) throws Exception {
		List<AttachmentEntity> uploadedFiles = new ArrayList<AttachmentEntity>(files.length * 2);
		for (MultipartFile file : files) {
			try {
				if (file.isEmpty())
					continue;
				// 文件存放服务端的位置
				File dir = new File(saveDir + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();
				// 写文件到服务器
				File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
				file.transferTo(serverFile);

				AttachmentEntity attachment = new AttachmentEntity();

				String originalFilename = file.getOriginalFilename();
				String fileSavedPath = serverFile.getPath();
				long fileSize = file.getSize();

				attachment.setName(originalFilename);
				attachment.setFileSize(fileSize);
				attachment.setPath(fileSavedPath);

				int lastDotIndex = originalFilename.lastIndexOf(".");
				if (lastDotIndex > 0) {
					String fileType = originalFilename.substring(lastDotIndex);
					attachment.setFileType(fileType);
				} else {
					attachment.setFileType("");
				}

				attachment.setUploadTime(new Date());

				save(attachment);
				uploadedFiles.add(attachment);
			} catch (Exception e) {
				logger.error("upload file error," + file.getOriginalFilename(), e);
				throw e;
			}
		}
		return uploadedFiles;
	}

	@Override
	public List<AttachmentEntity> getAttachmentsByMaterialId(Long materialId) {
		return attachmentDao.getAttachmentsByMaterialId(materialId);
	}

	@Override
	public void delFromMaterial(Long attachmentId) {
		attachmentDao.updateMaterialIdNull(attachmentId);
	}

	@Override
	public void updateMaterialId(AttachmentEntity attachment) {
		attachmentDao.updateMaterialId(attachment);
	}
}
