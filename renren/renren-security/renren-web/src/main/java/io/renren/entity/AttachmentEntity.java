package io.renren.entity;

import java.util.Date;

/**
 * 素材文件表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-23 19:10:33
 */
public class AttachmentEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 素材id
	private Long materialId;
	// 文件原始名
	private String name;
	// 文件保存路径
	private String path;
	// 文件大小
	private Long fileSize;
	// 文件类型
	private String fileType;
	// 上传时间
	private Date uploadTime;

	/**
	 * 设置：素材id
	 */
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	/**
	 * 获取：素材id
	 */
	public Long getMaterialId() {
		return materialId;
	}

	/**
	 * 设置：文件原始名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：文件原始名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：文件保存路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 获取：文件保存路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置：文件大小
	 */
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 获取：文件大小
	 */
	public Long getFileSize() {
		return fileSize;
	}

	/**
	 * 设置：文件类型
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * 获取：文件类型
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * 设置：上传时间
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * 获取：上传时间
	 */
	public Date getUploadTime() {
		return uploadTime;
	}
}
