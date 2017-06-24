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

import io.renren.entity.KeywordEntity;
import io.renren.service.KeywordService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

@RestController
@RequestMapping("keyword")
public class KeywordController {
	@Autowired
	private KeywordService keywordService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("keyword:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);

		List<KeywordEntity> keywordList = keywordService.queryList(query);
		int total = keywordService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(keywordList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("keyword:info")
	public R info(@PathVariable("id") Long id) {
		KeywordEntity keyword = keywordService.queryObject(id);

		return R.ok().put("keyword", keyword);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("keyword:save")
	public R save(@RequestBody KeywordEntity keyword) {
		keywordService.save(keyword);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("keyword:update")
	public R update(@RequestBody KeywordEntity keyword) {
		keywordService.update(keyword);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("keyword:delete")
	public R delete(@RequestBody Long[] ids) {
		keywordService.deleteBatch(ids);

		return R.ok();
	}

}
