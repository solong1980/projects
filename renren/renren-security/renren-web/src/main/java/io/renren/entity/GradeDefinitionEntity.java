package io.renren.entity;

import java.io.Serializable;

/**
 * 会员等级定义表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-30 20:24:04
 */
public class GradeDefinitionEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 等级,排序用
	private Integer level;
	// 等级名称
	private String name;
	// 状态,0 生效 ,1 失效
	private Integer status;

	/**
	 * 设置：等级,排序用
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 获取：等级,排序用
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * 设置：等级名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：等级名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：状态,0 生效 ,1 失效
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态,0 生效 ,1 失效
	 */
	public Integer getStatus() {
		return status;
	}

}
