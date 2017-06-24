package io.renren.controller.keyword;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.entity.KeywordsEntity;
import io.renren.service.KeywordsService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

@RestController
@RequestMapping("keywords")
public class KeywordsController {
	@Autowired
	private KeywordsService keywordsService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("keywords:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);

		List<KeywordsEntity> keywordsList = keywordsService.queryList(query);
		int total = keywordsService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(keywordsList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("keywords:info")
	public R info(@PathVariable("id") Long id) {
		KeywordsEntity keywords = keywordsService.queryObject(id);

		return R.ok().put("keywords", keywords);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("keywords:save")
	public R save(@RequestBody KeywordsEntity keywords) {
		keywordsService.save(keywords);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("keywords:update")
	public R update(@RequestBody KeywordsEntity keywords) {
		keywordsService.update(keywords);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("keywords:delete")
	public R delete(@RequestBody Long[] ids) {
		keywordsService.deleteBatch(ids);

		return R.ok();
	}

}
