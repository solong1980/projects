package io.renren.controller.tag;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.annotation.SysLog;
import io.renren.entity.MaterialTagEntity;
import io.renren.service.MaterialTagService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.ShiroUtils;
import io.renren.validator.ValidatorUtils;

/**
 * 
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-24 11:19:40
 */
@RestController
@RequestMapping("materialtag")
public class MaterialTagController {
	@Autowired
	private MaterialTagService materialTagService;

	/**
	 * 模糊查询
	 */
	@RequestMapping("/search")
	@RequiresPermissions("materialtag:search")
	public R search(@RequestParam Map<String, Object> params) {
		// 模糊查询
		List<MaterialTagEntity> materialTagsList = materialTagService.doSearch(params);
		return R.ok().put("list", materialTagsList);
	}

	/**
	 * 根据ids查询
	 */
	@RequestMapping("/queryByIds")
	//@RequiresPermissions("materialtag:queryByIds")
	public R queryByIds(@RequestBody Long[] ids) {
		// 查询列表数据
		List<MaterialTagEntity> materialTagsList = materialTagService.queryByIds(ids);
		return R.ok().put("materialTags", materialTagsList);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("materialtag:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);

		List<MaterialTagEntity> materialTagsList = materialTagService.queryList(query);
		int total = materialTagService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(materialTagsList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("materialtag:info")
	public R info(@PathVariable("id") Long id) {
		MaterialTagEntity materialTag = materialTagService.queryObject(id);

		return R.ok().put("materialTag", materialTag);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("materialtag:save")
	@SysLog("save material tag")
	public R save(@RequestBody MaterialTagEntity materialTag) {
		ValidatorUtils.validateEntity(materialTag);
		materialTag.setCreaterId(ShiroUtils.getUserId());
		materialTag.setCreateTime(MaterialTagEntity.getFastDate());
		materialTagService.save(materialTag);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("materialtag:update")
	public R update(@RequestBody MaterialTagEntity materialTag) {
		ValidatorUtils.validateEntity(materialTag);
		materialTagService.update(materialTag);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("materialtag:delete")
	public R delete(@RequestBody Long[] ids) {
		materialTagService.deleteBatch(ids);

		return R.ok();
	}

}
