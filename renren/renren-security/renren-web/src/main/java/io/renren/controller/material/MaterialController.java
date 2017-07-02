package io.renren.controller.material;

import io.renren.annotation.SysLog;
import io.renren.controller.AbstractController;
import io.renren.entity.MaterialEntity;
import io.renren.entity.MaterialPriceEntity;
import io.renren.service.MaterialService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.ShiroUtils;
import io.renren.validator.ValidatorUtils;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 素材信息
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017年6月22日 下午8:00:00
 */

@RestController
@RequestMapping("material")
public class MaterialController extends AbstractController {

	public MaterialController() {
		super();
	}

	@Autowired
	private MaterialService materialService;

	@RequestMapping("/list")
	@RequiresPermissions("material:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<MaterialEntity> materialList = materialService.queryList(query);
		int total = materialService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(materialList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	@RequestMapping("/info/{id}")
	@RequiresPermissions("material:info")
	public R info(@PathVariable("id") Long id) {
		MaterialEntity material = materialService.queryObject(id);
		return R.ok().put("material", material);
	}

	@SysLog("保存素材")
	@RequestMapping("/save")
	@RequiresPermissions("material:save")
	public R save(@RequestBody MaterialEntity material) {
		ValidatorUtils.validateEntity(material);
		for (MaterialPriceEntity materialPrice : material.getMaterialPrices()) {
			ValidatorUtils.validateEntity(materialPrice);
		}
		
		Long userId = ShiroUtils.getUserId();

		material.setCreateTime(MaterialEntity.getFastDate());
		material.setCreaterId(userId);

		materialService.saveAndWriteBackFileId(material);
		return R.ok();
	}

	@SysLog("修改素材")
	@RequestMapping("/update")
	@RequiresPermissions("material:update")
	public R update(@RequestBody MaterialEntity material) {
		Long userId = ShiroUtils.getUserId();
		material.setUpdateTime(MaterialEntity.getFastDate());
		material.setUpdaterId(userId);
		ValidatorUtils.validateEntity(material);
		materialService.updateAndWriteBackFileId(material);
		return R.ok();
	}

	@SysLog("删除素材")
	@RequestMapping("/delete")
	@RequiresPermissions("material:delete")
	public R delete(@RequestBody Long[] ids) {
		materialService.deleteBatch(ids);

		return R.ok();
	}

}
