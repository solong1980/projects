package io.renren.controller.attachment;

import io.renren.entity.AttachmentEntity;
import io.renren.service.AttachmentService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 素材文件表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-23 19:10:33
 */
@RestController
@RequestMapping("attachment")
public class AttachmentController {

	private static Logger logger = LoggerFactory.getLogger(AttachmentController.class);

	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 上传
	 */
	@RequestMapping("/file/upload")
	// @RequiresPermissions("attachment:upload")
	public List<Long> uploadFileHandler(@RequestParam("file") MultipartFile[] files, HttpSession session) {
		if (files != null && files.length > 0) {
			String saveDir = session.getServletContext().getRealPath("/Images");
			try {
				return attachmentService.uploadFile(saveDir, files);
			} catch (Exception e) {
				logger.error("upload file failure", e);
				e.printStackTrace();
			}
		}
		return Collections.emptyList();
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("attachment:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);

		List<AttachmentEntity> attachmentList = attachmentService.queryList(query);
		int total = attachmentService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(attachmentList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("attachment:info")
	public R info(@PathVariable("id") Long id) {
		AttachmentEntity attachment = attachmentService.queryObject(id);

		return R.ok().put("attachment", attachment);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("attachment:save")
	public R save(@RequestBody AttachmentEntity attachment) {
		attachmentService.save(attachment);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("attachment:update")
	public R update(@RequestBody AttachmentEntity attachment) {
		attachmentService.update(attachment);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("attachment:delete")
	public R delete(@RequestBody Long[] ids) {
		attachmentService.deleteBatch(ids);
		return R.ok();
	}

}
