package com.yz.boster.controller.download;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yz.boster.commons.base.BaseController;

@Controller
@RequestMapping("/file")
public class FileDownloadController extends BaseController {

	@GetMapping("/download/{fileId}")
	public void webUploadFile(@PathVariable("fileId") String fileId, HttpServletRequest request,
			HttpServletResponse response) {

		String fileName = fileId.toString();
		fileName = fileName+".JPG";
		// 拼接真实路径
		String realPath = request.getServletContext().getRealPath(
				"/Images/tmpFiles")
				+ File.separator + fileName;
		// 读取文件
		File file = new File(realPath);
		if (file.exists()) {
			OutputStream os = null;
			try {
				os = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				if (request.getHeader("User-Agent").toUpperCase()
						.indexOf("MSIE") > 0) { // IE浏览器
					fileName = URLEncoder.encode(fileName, "UTF-8");
				} else {
					fileName = URLDecoder.decode(fileName,
							System.getProperty("file.encoding"));// 其他浏览器
				}

				response.setHeader(
						"Content-disposition",
						"attachment; filename="
								+ new String(fileName.getBytes("utf-8"),
										"ISO8859-1")); // 指定下载的文件名
				os.write(FileUtils.readFileToByteArray(file));
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

}
