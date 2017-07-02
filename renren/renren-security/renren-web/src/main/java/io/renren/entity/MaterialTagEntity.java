package io.renren.entity;

import org.hibernate.validator.constraints.NotBlank;


/**
 * 
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-24 11:19:40
 */
public class MaterialTagEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 标签名字
	@NotBlank(message="标签名称不能为空")
	private String tagName;
	// 标签描述
	@NotBlank(message="标签定义不能为空")
	private String tagDescription;
	// 状态(预留)
	private Integer status;

	/**
	 * 设置：标签名字
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * 获取：标签名字
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * 设置：标签描述
	 */
	public void setTagDescription(String tagDescription) {
		this.tagDescription = tagDescription;
	}

	/**
	 * 获取：标签描述
	 */
	public String getTagDescription() {
		return tagDescription;
	}

	/**
	 * 设置：状态(预留)
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态(预留)
	 */
	public Integer getStatus() {
		return status;
	}

}
