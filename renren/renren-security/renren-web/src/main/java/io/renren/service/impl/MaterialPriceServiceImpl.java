package io.renren.service.impl;

import io.renren.dao.MaterialPriceDao;
import io.renren.entity.MaterialPriceEntity;
import io.renren.service.MaterialPriceService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("materialPriceService")
public class MaterialPriceServiceImpl implements MaterialPriceService {
	@Autowired
	private MaterialPriceDao materialPriceDao;

	@Override
	public MaterialPriceEntity queryObject(Long id) {
		return materialPriceDao.queryObject(id);
	}

	@Override
	public List<MaterialPriceEntity> queryList(Map<String, Object> map) {
		return materialPriceDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return materialPriceDao.queryTotal(map);
	}

	@Override
	public void save(MaterialPriceEntity materialPrice) {
		materialPriceDao.save(materialPrice);
	}

	@Override
	public void update(MaterialPriceEntity materialPrice) {
		materialPriceDao.update(materialPrice);
	}

	@Override
	public void delete(Long id) {
		materialPriceDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		materialPriceDao.deleteBatch(ids);
	}

	@Override
	public List<MaterialPriceEntity> getMaterialPriceSettings(Long materialId) {
		List<MaterialPriceEntity> materialPrices = materialPriceDao.getMaterialPriceSettings(materialId);
		return materialPrices;
	}

	@Override
	public void checkUpdateMaterialPriceSetting(MaterialPriceEntity materialPrice) {
		// 调用sql判断,所有属性一样则返回1 ,否则返回0
		Long changedCount = materialPriceDao.checkPriceSettingChanged(materialPrice);
		if (changedCount != null && changedCount == 0) {
			materialPriceDao.updatePriceSettingStatus(materialPrice.getId());
			materialPriceDao.save(materialPrice);
		} else {
			return;
		}
	}

	@Override
	public void updateMaterialPriceSettings(Long materialId, Integer materialStatus,
			List<MaterialPriceEntity> materialPrices) {

		if (materialPrices != null && !materialPrices.isEmpty()) {
			for (MaterialPriceEntity materialPrice : materialPrices) {
				// 强调设置素材ID
				materialPrice.setMaterialId(materialId);
				Long id = materialPrice.getId();
				if (id == null) {
					// 如果配置信息没有id则保存
					materialPriceDao.save(materialPrice);
					continue;
				}
				// 状态:0未发布,50发布,200:下架
				switch (materialStatus) {
				case 0:
					// 直接更新
					update(materialPrice);
					break;
				case 50:
					// 否则做比较更新处理
					checkUpdateMaterialPriceSetting(materialPrice);
					break;
				default:
					break;
				}
			}
		}
	}

}
