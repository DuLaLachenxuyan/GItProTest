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

import com.trw.constant.Constant;
import com.trw.model.TrwTFile;
import com.trw.qny.QnyFileUtil;
import com.trw.service.IFileService;
import com.trw.util.DateTimeKit;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;

import io.swagger.annotations.ApiOperation;

@RestController
public class FileController extends BaseController {
	@Autowired
	private IFileService fileService;

	@RequestMapping(value = "/updateFile")
	@ApiOperation(httpMethod = "POST", value = "文件上传后图片状态修改", notes = "文件上传后图片状态修改")
	public ResultMsg<String> updateFile(String imgs, String userId) {
		fileService.updateFile(imgs, userId);
		ResultMsg<String> result = new ResultMsg<String>();
		result.setCode(Constant.CODE_SUCC);
		return result;
	}

	@RequestMapping(value = "/deleteFile")
	@ApiOperation(httpMethod = "POST", value = "图片过期删除", notes = "图片过期删除")
	public ResultMsg<String> deleteFile() {
		fileService.deleteOverData();
		ResultMsg<String> result = new ResultMsg<String>();
		result.setCode(Constant.CODE_SUCC);
		return result;
	}

	/**
	 * @Title: qnyUploadFile
	 * @Description: 七牛云文件上传
	 * @author luojing
	 * @param @param
	 *            file
	 * @param @return
	 * @param @throws
	 *            QiniuException
	 * @param @throws
	 *            IOException 参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date
	 *             2018年7月7日
	 */
	@RequestMapping(value = "/qnyUploadFile", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "七牛云文件上传", notes = "七牛云文件上传")
	public ResultMsg<String> qnyUploadFile(@RequestParam(value = "file", required = true) MultipartFile file) {
		ResultMsg<String> msg = QnyFileUtil.uploadFile(file);
		if (Constant.CODE_SUCC.equals(msg.getCode())) {
			TrwTFile entity = new TrwTFile();
			entity.setfId(ToolUtil.generateId("FLD"));
			entity.setFileName(msg.getData());
			entity.setExpDate(DateTimeKit.offsiteDay(new Date(), 1));
			fileService.insert(entity);
		}
		return msg;

	}

	@ApiOperation(httpMethod = "POST", value = "七牛云多个文件上传", notes = "多个文件上传")
	@RequestMapping(value = "/qnyUploadMult", method = RequestMethod.POST)
	public String uploadMult(@RequestParam("file") MultipartFile[] files, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (MultipartFile f : files) {
			ResultMsg<String> msg = QnyFileUtil.uploadFile(f);
			if (Constant.CODE_SUCC.equals(msg.getCode())) {// 成功
				String fileUrl = msg.getData();
				TrwTFile entity = new TrwTFile();
				entity.setfId(ToolUtil.generateId("FLD"));
				entity.setFileName(fileUrl);
				entity.setExpDate(DateTimeKit.offsiteDay(new Date(), 1));
				fileService.insert(entity);
				sb.append(fileUrl + ",");
			}
		}
		String re = StrKit.removeSuffix(sb.toString(), ",");
		return re;
	}

	/**
	 * @Title: batchDeleteFile
	 * @Description: 七牛云批量删除图片
	 * @author luojing
	 * @param @param
	 *            file
	 * @param @return
	 * @param @throws
	 *            QiniuException
	 * @param @throws
	 *            IOException 参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date
	 *             2018年7月7日
	 */
	@RequestMapping(value = "/batchDeleteFile", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "七牛云批量删除图片", notes = "七牛云批量删除图片")
	public ResultMsg<String> batchDeleteFile(@RequestParam("fileNames") String fileNames) {
		return QnyFileUtil.batchDeleteFile(fileNames);
	}

	/**
	 * @Title: qnyDelImages
	 * @Description: 七牛云删除图片
	 * @author luojing
	 * @param @param
	 *            file
	 * @param @return
	 * @param @throws
	 *            QiniuException
	 * @param @throws
	 *            IOException 参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date
	 *             2018年7月7日
	 */
	@RequestMapping(value = "/qnyDelImages", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "七牛云删除图片", notes = "七牛云删除图片")
	public ResultMsg<String> qnyDelImages(@RequestParam("fileName") String fileName) {
		return QnyFileUtil.deleteFile(fileName);
	}
}
