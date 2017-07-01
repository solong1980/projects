package io.renren.service;

import io.renren.entity.MaterialPriceEntity;

import java.util.List;
import java.util.Map;

/**
 * 素材价格表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-230 19:10:33
 */
public interface MaterialPriceService {

	MaterialPriceEntity queryObject(Long id);

	List<MaterialPriceEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void save(MaterialPriceEntity materialPrice);

	void update(MaterialPriceEntity materialPrice);

	void delete(Long id);

	void deleteBatch(Long[] ids);

	List<MaterialPriceEntity> getMaterialPriceSettings(Long materialId);

	/**
	 * 先比较没项数据数据是否有变化,再判断是否做后续操作 在价格配置更新时对现有数据作状态修改,然后再新加一条
	 * 
	 * Backup update status to 10 <br>
	 * Add new recode with status 0
	 * 
	 * @param id
	 * @param status
	 */
	void checkUpdateMaterialPriceSetting(MaterialPriceEntity materialPrice);

	/**
	 * 根据素材状态判断是否可以直接作更新, 未发布的状态直接更新 ,已发布的状态做状态修改为历史态,再新增
	 * 
	 * @param materialStatus
	 * @param materialPrice
	 */
	void updateMaterialPriceSettings(Long materialId,Integer materialStatus, List<MaterialPriceEntity> materialPrices);

}
