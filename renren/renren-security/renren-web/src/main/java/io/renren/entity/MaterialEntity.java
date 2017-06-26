package io.renren.entity;

/**
 * 素材表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-22 20:24:04
 */
public class MaterialEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer status = 0;
	private String name;
	private String tagIds;
	private String keywordIds;
	private Integer type = 0;// 0为未知类型
	private Long makerid;
	private String maker;
	private Integer fileCount;
	private String description;

	// 文件实体
	private AttachmentEntity[] attachments;

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setKeywordIds(String keywordIds) {
		this.keywordIds = keywordIds;
	}

	public String getKeywordIds() {
		return keywordIds;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setMakerid(Long makerid) {
		this.makerid = makerid;
	}

	public Long getMakerid() {
		return makerid;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getMaker() {
		return maker;
	}

	public Integer getFileCount() {
		return fileCount;
	}

	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public AttachmentEntity[] getAttachments() {
		return attachments;
	}

	public void setAttachments(AttachmentEntity[] attachments) {
		this.attachments = attachments;
	}

}
