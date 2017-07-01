package io.renren.controller.material;

import io.renren.entity.MaterialPriceEntity;
import io.renren.service.MaterialPriceService;
import io.renren.utils.R;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 素材价格表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-30 18:51:20
 */
@RestController
@RequestMapping("materialprice")
public class MaterialPriceController {
	@Autowired
	private MaterialPriceService materialPriceService;

	/**
	 * 素材等级相关价格配置列表
	 */
	@RequestMapping("/materialpricesettings/{materialId}")
	public R list(@PathVariable("materialId") Long materialId) {
		// 查询列表数据
		List<MaterialPriceEntity> materialPriceSettings = materialPriceService.getMaterialPriceSettings(materialId);
		return R.ok().put("materialPriceSettings", materialPriceSettings);
	}

}
