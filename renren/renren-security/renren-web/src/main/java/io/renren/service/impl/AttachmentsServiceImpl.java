package io.renren.service.impl;

import io.renren.dao.AttachmentsDao;
import io.renren.entity.AttachmentsEntity;
import io.renren.service.AttachmentsService;

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

@Service("attachmentsService")
public class AttachmentsServiceImpl implements AttachmentsService {

	private static Logger logger = LoggerFactory.getLogger(AttachmentsServiceImpl.class);

	@Autowired
	private AttachmentsDao attachmentsDao;

	@Override
	public AttachmentsEntity queryObject(Long id) {
		return attachmentsDao.queryObject(id);
	}

	@Override
	public List<AttachmentsEntity> queryList(Map<String, Object> map) {
		return attachmentsDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return attachmentsDao.queryTotal(map);
	}

	@Override
	public void save(AttachmentsEntity attachments) {
		attachmentsDao.save(attachments);
	}

	@Override
	public void update(AttachmentsEntity attachments) {
		attachmentsDao.update(attachments);
	}

	@Override
	public void delete(Long id) {
		attachmentsDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		attachmentsDao.deleteBatch(ids);
	}

	@Override
	public List<Long> uploadFile(String saveDir, MultipartFile[] files) throws Exception {
		List<Long> fIds = new ArrayList<Long>(files.length * 2);
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

				AttachmentsEntity attachment = new AttachmentsEntity();

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
				fIds.add(attachment.getId());
			} catch (Exception e) {
				logger.error("upload file error," + file.getOriginalFilename(), e);
				throw e;
			}
		}
		return fIds;
	}

}
