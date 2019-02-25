package com.fastdfs.component;


import com.fastdfs.conf.FastConfig;
import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * FastDFS文件上传下载包装类
 * @author lihaichao
 */
@Component
public class FastDFSClientWrapper {

    private static final Logger logger = Logger.getLogger(FastDFSClientWrapper.class);

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private FastConfig fastConfig;   // 项目参数配置

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public Map<String,Object> uploadFile(MultipartFile file) throws IOException {
        Map<String,Object> map = new HashMap<>();
        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        String path = getResAccessUrl(storePath);
        if(path !=null && !path.equals("")){
            map.put("code",0);
            map.put("msg","上传成功");
            map.put("data",path);
        }else{
            map.put("code",-1);
            map.put("msg","上传失败");
            map.put("data",null);
        }
        return map;
    }


    /**
     * 上传图片 并生成缩略图
     * @param file
     * @return map
     * @throws IOException
     */
    public Map<String,Object> uploadImage(MultipartFile file) throws IOException {
        Map<String,Object> map = new HashMap<>();
        //StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        StorePath storePath1 = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        //String imagePath =  getResAccessUrl(storePath);
        String thumbImagePath =  getResAccessUrl(storePath1);
        if(thumbImagePath !=null && !thumbImagePath.equals("")){
            map.put("code",0);
            map.put("msg","上传成功");
            map.put("data",thumbImagePath);
        }else{
            map.put("code",-1);
            map.put("msg","上传失败");
            map.put("data",null);
        }
        return map;
    }


    /**
     * 将一段字符串生成一个文件上传
     * @param content 文件内容
     * @param fileExtension
     * @return 获取封装后文件完整URL地址
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream,buff.length, fileExtension,null);
        return getResAccessUrl(storePath);
    }


    /**
     * 获取封装后文件完整URL地址
     * @param storePath
     */
    private String getResAccessUrl(StorePath storePath) {
        return "http://"+fastConfig.getResHost()+":"+fastConfig.getStoragePort()+"/"+storePath.getFullPath();
    }

    /**
     * 获取文件信息
     * @param fileUrl 文件地址
     */
    public FileInfo getFileInfo(String fileUrl) {
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            return storageClient.queryFileInfo(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }


    /**
     * 下载文件
     * @param fileUrl 文件路径
     */
    public void downFile(String fileUrl){
        StorePath storePath = StorePath.parseFromUrl(fileUrl);
        storageClient.downloadFile(storePath.getGroup(), storePath.getPath(),null);
    }


    /**
     * 获取元数据
     * @param fileUrl
     */
    public Set<MetaData> getMateData(String fileUrl){
        StorePath storePath = StorePath.parseFromUrl(fileUrl);
        return storageClient.getMetadata(storePath.getGroup(), storePath.getPath());
    }



    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }

}
