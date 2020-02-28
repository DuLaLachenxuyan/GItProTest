package com.trw.config;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.trw.util.StrKit;

@Component
public class FastDFSClientWrapper {

    private final Logger logger = LoggerFactory.getLogger(FastDFSClientWrapper.class);
    @Autowired
    private FastFileStorageClient storageClient;
    @Value("${fdfs.proxyurl}")
    private String proxyurl;
    /**
     * 图片上传
     * @param file
     * @return
     * @throws IOException
     */
   public String uploadFile(MultipartFile file) throws IOException {
	   if(file == null) {
		   logger.error("file is null");
		   return null;
	   }
       StorePath storePath = storageClient.uploadFile((InputStream)file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
       return getResAccessUrl(storePath);
   }
   
   /**
    * 返回文件完整URL地址
    * @param storePath
    * @return
    */
   private String getResAccessUrl(StorePath storePath) {
       String fileUrl = "http://"+ proxyurl + "/" + storePath.getFullPath();
       return fileUrl;
   }
   
   /**
    * 删除文件
    * @param fileUrl 文件访问地址
    * @return
    */
   public void deleteFile(String fileUrl) {
       if (StrKit.isBlank(fileUrl)) {
           return;
       }
       try {
           StorePath storePath = StorePath.praseFromUrl(fileUrl);
           storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
       } catch (FdfsUnsupportStorePathException e) {
           logger.error(e.getMessage());
       }
   }
}