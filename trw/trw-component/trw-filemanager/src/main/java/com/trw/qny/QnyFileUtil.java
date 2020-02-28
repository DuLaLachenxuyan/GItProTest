package com.trw.qny;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.trw.constant.Constant;
import com.trw.util.DateUtil;
import com.trw.vo.ResultMsg;

/**
* @ClassName: QnyFileUtil 
* @Description: 七牛云文件管理工具类型
* @author luojing
* @date 2018年7月7日 下午2:38:05 
*
 */
@Component
public class QnyFileUtil {
	
	/**
	 * accessKey
	 */
	private static final String ACCESS_KEY = "rCepLWDYD59tKRm_hQ-uc4nK-4Ax2dNT2MzLn54_";
	/**
	 * secretKey
	 */
	private static final String SECRET_KEY = "NheEtTf7WMI2gtktfHiWYJBMt5stQfyTIVwvblHm";
	/**
	 * bucket 访问空间
	 */
	private static String BUCKET_NAME = "turongw1";
	
	@Value("${qiniu.bucket}")
	public void setBucket(String bucket) {
		QnyFileUtil.BUCKET_NAME = bucket;
	}
	/**
	 * 空间访问域名
	 */
	private static String BUCKET_HOST_NAME = "http://img.turongw.cn/";
	
	@Value("${qiniu.hostName}")
	public void setHostName(String hostName) {
		QnyFileUtil.BUCKET_HOST_NAME = hostName;
	}
	/**
	 * 
	 */
	private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	/**
	 * 地区配置
	 */
	private static Configuration cfg = new Configuration(Zone.zone2());
	
	/**
	* @Title: getUpToken 
	* @Description: 获取upToken
	* @author luojing
	* @param @return  参数说明 
	* @return String 返回类型 
	* @throws 
	* @date 2018年7月7日
	 */
	public static String getUpToken() {
		String upToken = auth.uploadToken(BUCKET_NAME);
		return upToken;
	}
	
	/**
	* @Title: upload 
	* @Description: 上传
	* @author luojing
	* @param @param file
	* @param @return  参数说明 
	* @return Map<String,String> 返回类型 
	 * @throws IOException 
	 * @throws QiniuException 
	* @throws 
	* @date 2018年7月7日
	 */
	public static ResultMsg<String> uploadFile(MultipartFile file){
		ResultMsg<String> result = new ResultMsg<String>();
		try {
			//获取文件名
			String fileName = file.getOriginalFilename();
			//截取文件后缀
			String suffix = fileName.substring(fileName.indexOf("."));
			//重新生成文件名
			String key = DateUtil.getAllTime() + suffix;
			UploadManager upload = new UploadManager(cfg);
			//字节流方式上传
			Response response = upload.put(file.getInputStream(), key, getUpToken(),null,null);
			//上传成功
			if(response.statusCode == 200) {
				result.setCode(Constant.CODE_SUCC);
				result.setData(BUCKET_HOST_NAME + key);
			}else {
				result.setCode(Constant.CODE_FAIL);
			}
		} catch (QiniuException e) {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg(e.response.toString());
		} catch (IOException e) {
			result.setCode(Constant.CODE_FAIL);
			result.setCode(e.getMessage());
		}
		return result;
	}
	
	/**
	* @Title: deleteFile 
	* @Description: 删除文件
	* @author luojing
	* @param @param fileUrl 文件路径
	* @param @return  参数说明 
	* @return ResultMsg<String> 返回类型 
	* @throws 
	* @date 2018年7月7日
	 */
	public static ResultMsg<String> deleteFile(String fileUrl){
		ResultMsg<String> result = new ResultMsg<String>();
		try {
			//获取文件名
			String key = getFileName(fileUrl);
			BucketManager bucketManager = new BucketManager(auth, cfg);
			Response response = bucketManager.delete(BUCKET_NAME, key);
			//删除成功
			if(response.statusCode == 200) {
				result.setCode(Constant.CODE_SUCC);
			}else {
				result.setCode(Constant.CODE_FAIL);
			}
		} catch (QiniuException e) {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg(e.response.toString());
		}
		return result;
	}
	
	/**
	* @Title: batchDeleteFile 
	* @Description: 批量删除图片   单次批量请求的文件数量不得超过1000
	* @author luojing
	* @param @param fileNames 图片名字集合
	* @param @return  参数说明 
	* @return ResultMsg<String> 返回类型 
	* @throws 
	* @date 2018年7月7日
	 */
	public static ResultMsg<String> batchDeleteFile(String fileNames){
		ResultMsg<String> result = new ResultMsg<String>();
		try {
			//文件名集合
			String [] fileList = fileNames.split(",");
			String [] keyList = new String[fileList.length];
			for(int i=0;i<fileList.length;i++) {
				String key = getFileName(fileList[i]);
				keyList[i] = key;
			}
			BucketManager bucketManager = new BucketManager(auth, cfg);
			BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
			batchOperations.addDeleteOp(BUCKET_NAME, keyList);
			//批量删除
			bucketManager.batch(batchOperations);
			/*Response response = bucketManager.batch(batchOperations);
			BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
			for (int i = 0; i < keyList.length; i++) {
		        BatchStatus status = batchStatusList[i];
		        String key = keyList[i];
		        System.out.print(key + "\t");
		        if (status.code == 200) {
		            System.out.println("delete success");
		        } else {
		            System.out.println(.);
		        }
		    }*/
			result.setCode(Constant.CODE_SUCC);
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	* @Title: subUrl 
	* @Description: 截取url,返回文件名
	* @author luojing
	* @param @param url
	* @param @return  参数说明 
	* @return String 返回类型 
	* @throws 
	* @date 2018年7月7日
	 */
	public static String getFileName(String url) {
		return url.substring(url.lastIndexOf("/")+1);
	}
}
