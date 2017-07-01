package io.renren.dao;

import java.util.List;

import io.renren.entity.MaterialPriceEntity;

/**
 * 素材价格表
 * 
 */
public interface MaterialPriceDao extends BaseDao<MaterialPriceEntity> {
	List<MaterialPriceEntity> getMaterialPriceSettings(Long materialId);

	Long checkPriceSettingChanged(MaterialPriceEntity materialPriceEntity);

	void updatePriceSettingStatus(Long id);
}
