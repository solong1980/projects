package com.yz.boster.controller.upload;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yz.boster.commons.base.BaseController;
import com.yz.boster.model.vo.UploadConfigVo;

@Controller
@RequestMapping("/file")
public class FileUploadController extends BaseController {
	@GetMapping("/uploadConfig")
	@ResponseBody
	public UploadConfigVo uploadConfig(Model model) {
		UploadConfigVo configVo = new UploadConfigVo();
		configVo.setMaxSize(10000);
		configVo.setFileMaxCount(10);
		return configVo;
	}

	@GetMapping("/webUploadFile")
	public String webUploadFile(Model model) {
		return "file/webupload";
	}

	@GetMapping("/plUploadFile")
	public String plUploadFile(Model model) {
		return "file/plupload";
	}

	@GetMapping("/plUploadUiFile")
	public String plUploadUiFile(Model model) {
		return "file/plupload_ui";
	}

	@PostMapping(value = "/upload")
	@ResponseBody
	public String uploadFileHandler(
			@RequestParam("file") MultipartFile[] files, HttpSession session) {
		if (files != null && files.length > 0) {
			String path = session.getServletContext().getRealPath("/Images");
			for (MultipartFile file : files) {
				try {
					if (file.isEmpty())
						continue;
					// 文件存放服务端的位置
					File dir = new File(path + File.separator + "tmpFiles");
					if (!dir.exists())
						dir.mkdirs();
					// 写文件到服务器
					File serverFile = new File(dir.getAbsolutePath()
							+ File.separator + file.getOriginalFilename());
					file.transferTo(serverFile);
					return file.getOriginalFilename();// UUID.randomUUID().toString();
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			return "";
		} else {
			return "";
		}
	}

}
