package io.renren.controller.grade;

import io.renren.entity.GradeDefinitionEntity;
import io.renren.service.GradeDefinitionService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

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
 * 会员等级定义表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-30 18:51:20
 */
@RestController
@RequestMapping("gradedefinition")
public class GradeDefinitionController {
	@Autowired
	private GradeDefinitionService gradeDefinitionService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("gradedefinition:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);

		List<GradeDefinitionEntity> gradeDefinitionList = gradeDefinitionService.queryList(query);
		int total = gradeDefinitionService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(gradeDefinitionList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("gradedefinition:info")
	public R info(@PathVariable("id") Long id) {
		GradeDefinitionEntity gradeDefinition = gradeDefinitionService.queryObject(id);

		return R.ok().put("tGradeDefinition", gradeDefinition);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("gradedefinition:save")
	public R save(@RequestBody GradeDefinitionEntity gradeDefinition) {
		gradeDefinitionService.save(gradeDefinition);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("gradedefinition:update")
	public R update(@RequestBody GradeDefinitionEntity gradeDefinition) {
		gradeDefinitionService.update(gradeDefinition);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("gradedefinition:delete")
	public R delete(@RequestBody Long[] ids) {
		gradeDefinitionService.deleteBatch(ids);

		return R.ok();
	}

}
