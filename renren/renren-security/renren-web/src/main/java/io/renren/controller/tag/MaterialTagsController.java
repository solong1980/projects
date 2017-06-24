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
import io.renren.entity.MaterialTagsEntity;
import io.renren.service.MaterialTagsService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.ShiroUtils;

/**
 * 
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-24 11:19:40
 */
@RestController
@RequestMapping("materialtags")
public class MaterialTagsController {
	@Autowired
	private MaterialTagsService materialTagsService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("materialtags:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);

		List<MaterialTagsEntity> materialTagsList = materialTagsService.queryList(query);
		int total = materialTagsService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(materialTagsList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("materialtags:info")
	public R info(@PathVariable("id") Long id) {
		MaterialTagsEntity materialTags = materialTagsService.queryObject(id);

		return R.ok().put("materialTags", materialTags);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("materialtags:save")
	@SysLog("save material tag")
	public R save(@RequestBody MaterialTagsEntity materialTags) {
		
		materialTags.setCreaterId(ShiroUtils.getUserId());
		materialTags.setCreateTime(MaterialTagsEntity.getFastDate());
		
		materialTagsService.save(materialTags);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("materialtags:update")
	public R update(@RequestBody MaterialTagsEntity materialTags) {
		materialTagsService.update(materialTags);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("materialtags:delete")
	public R delete(@RequestBody Long[] ids) {
		materialTagsService.deleteBatch(ids);

		return R.ok();
	}

}
