package io.renren.controller.material;

import io.renren.annotation.SysLog;
import io.renren.controller.AbstractController;
import io.renren.entity.MaterialsEntity;
import io.renren.service.MaterialsService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.ShiroUtils;
import io.renren.validator.ValidatorUtils;

import java.util.Date;
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
@RequestMapping("/material/manager")
public class MaterialController extends AbstractController {

	public MaterialController() {
		super();
	}

	@Autowired
	private MaterialsService materialsService;

	@RequestMapping("/list")
	@RequiresPermissions("material:manager:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<MaterialsEntity> materialsList = materialsService.queryList(query);
		int total = materialsService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(materialsList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	@RequestMapping("/info/{id}")
	@RequiresPermissions("material:manager:info")
	public R info(@PathVariable("id") Long id) {
		MaterialsEntity materials = materialsService.queryObject(id);
		return R.ok().put("materials", materials);
	}

	@SysLog("保存素材")
	@RequestMapping("/save")
	@RequiresPermissions("material:manager:save")
	public R save(@RequestBody MaterialsEntity materials) {
		Long userId = ShiroUtils.getUserId();

		materials.setCreateTime(MaterialsEntity.getFastDate());
		materials.setCreaterId(userId);
		
		ValidatorUtils.validateEntity(materials);
		materialsService.saveAndWriteBackFileId(materials);
		return R.ok();
	}

	@SysLog("修改素材")
	@RequestMapping("/update")
	@RequiresPermissions("material:manager:update")
	public R update(@RequestBody MaterialsEntity materials) {
		Long userId = ShiroUtils.getUserId();

		materials.setUpdateTime(MaterialsEntity.getFastDate());
		materials.setUpdaterId(userId);

		ValidatorUtils.validateEntity(materials);
		materialsService.update(materials);
		return R.ok();
	}

	@SysLog("删除素材")
	@RequestMapping("/delete")
	@RequiresPermissions("material:manager:delete")
	public R delete(@RequestBody Long[] ids) {
		materialsService.deleteBatch(ids);

		return R.ok();
	}

}
