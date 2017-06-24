package io.renren.entity;

/**
 * 
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-24 13:21:40
 */
public class KeywordEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 关键字
	private String keyword;
	// 描述
	private String description;
	// 状态
	private Integer status;

	/**
	 * 设置：关键字
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * 获取：关键字
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置：状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态
	 */
	public Integer getStatus() {
		return status;
	}

}
