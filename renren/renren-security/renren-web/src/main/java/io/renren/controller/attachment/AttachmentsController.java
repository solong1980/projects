package io.renren.controller.attachment;

import io.renren.entity.AttachmentsEntity;
import io.renren.service.AttachmentsService;
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
@RequestMapping("attachments")
public class AttachmentsController {

	private static Logger logger = LoggerFactory.getLogger(AttachmentsController.class);

	@Autowired
	private AttachmentsService attachmentsService;

	/**
	 * 上传
	 */
	@RequestMapping("/file/upload")
	//@RequiresPermissions("attachments:upload")
	public List<Long> uploadFileHandler(@RequestParam("file") MultipartFile[] files, HttpSession session) {
		if (files != null && files.length > 0) {
			String saveDir = session.getServletContext().getRealPath("/Images");
			try {
				return attachmentsService.uploadFile(saveDir, files);
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
	@RequiresPermissions("attachments:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);

		List<AttachmentsEntity> tAttachmentsList = attachmentsService.queryList(query);
		int total = attachmentsService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(tAttachmentsList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("attachments:info")
	public R info(@PathVariable("id") Long id) {
		AttachmentsEntity attachments = attachmentsService.queryObject(id);

		return R.ok().put("attachments", attachments);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("attachments:save")
	public R save(@RequestBody AttachmentsEntity attachments) {
		attachmentsService.save(attachments);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("attachments:update")
	public R update(@RequestBody AttachmentsEntity attachments) {
		attachmentsService.update(attachments);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("attachments:delete")
	public R delete(@RequestBody Long[] ids) {
		attachmentsService.deleteBatch(ids);
		return R.ok();
	}

}
