package io.renren.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

/**
 * 素材价格表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-22 20:24:04
 */
public class MaterialPriceEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private GradeDefinitionEntity gradeDefinition;

	// 素材id
	private Long materialId;
	// 等级id
	@NotNull
	private Long gradeId;
	// 消费获得积分
	@NotNull(message = "请填写消费获得积分")
	private Integer integral;
	// 价格
	@NotNull(message = "请填写素材价格")
	@Range(min = 0, max = 99999)
	private BigDecimal price;
	// 单位:10 人民币 , 20 金币
	@NotNull(message = "请选择单位")
	private Integer unit;

	// 前台设置是否发生变化
	private boolean changed;

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public GradeDefinitionEntity getGradeDefinition() {
		return gradeDefinition;
	}

	public void setGradeDefinition(GradeDefinitionEntity gradeDefinition) {
		this.gradeDefinition = gradeDefinition;
	}

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
	 * 设置：等级id
	 */
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	/**
	 * 获取：等级id
	 */
	public Long getGradeId() {
		return gradeId;
	}

	/**
	 * 设置：消费获得积分
	 */
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	/**
	 * 获取：消费获得积分
	 */
	public Integer getIntegral() {
		return integral;
	}

	/**
	 * 设置：价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 获取：价格
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置：单位:10 人民币 , 20 金币
	 */
	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	/**
	 * 获取：单位:10 人民币 , 20 金币
	 */
	public Integer getUnit() {
		return unit;
	}

}
