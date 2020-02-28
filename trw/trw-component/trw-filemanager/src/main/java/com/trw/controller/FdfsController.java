package com.trw.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trw.config.FastDFSClientWrapper;
import com.trw.model.TrwTFile;
import com.trw.service.IFileService;
import com.trw.util.DateTimeKit;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;

import io.swagger.annotations.ApiOperation;

@RestController
public class FdfsController extends BaseController{

	@Autowired
	private FastDFSClientWrapper fdfsClient;
	@Autowired
	private IFileService fileService;

	// 上传文件
	@ApiOperation(httpMethod = "POST", value = "文件上传", notes = "文件上传")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(MultipartFile file)
			throws Exception {
		String fileUrl = fdfsClient.uploadFile(file);
		TrwTFile entity = new TrwTFile();
		entity.setfId(ToolUtil.generateId("FLD"));
		entity.setFileName(fileUrl);
		entity.setExpDate(DateTimeKit.offsiteDay(new Date(), 1));
		fileService.insert(entity);
		return fileUrl;
	}

	// 上传多个文件
	@ApiOperation(httpMethod = "POST", value = "多个文件上传", notes = "多个文件上传")
	@RequestMapping(value = "/uploadMult", method = RequestMethod.POST)
	public String uploadMult(@RequestParam("file") MultipartFile[] files, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (MultipartFile f : files) {
			String fileUrl= fdfsClient.uploadFile(f);
			TrwTFile entity = new TrwTFile();
			entity.setfId(ToolUtil.generateId("FLD"));
			entity.setFileName(fileUrl);
			entity.setExpDate(DateTimeKit.offsiteDay(new Date(), 1));
			fileService.insert(entity);
			sb.append(fileUrl+",");
		}
		String re = StrKit.removeSuffix(sb.toString(),",");
		return re;
	}
}