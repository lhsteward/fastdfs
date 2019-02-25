package com.fastdfs.controller;


import com.fastdfs.component.FastDFSClientWrapper;
import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * fastDFS 文件操作
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Resource
    private FastDFSClientWrapper fcw;

    @PostMapping("uploadFile")
    public Map<String,Object> uploadFile(MultipartFile file) throws IOException {
        return fcw.uploadFile(file);
    }

    @PostMapping("uploadImage")
    public Map<String,Object> uploadImage(MultipartFile file) throws IOException {
        return fcw.uploadImage(file);
    }

    @GetMapping("getFileInfo")
    public FileInfo getFileInfo(String fileUrl) {
        return fcw.getFileInfo(fileUrl);
    }

    @PostMapping("downFile")
    public void downFile(String fileUrl){
        fcw.downFile(fileUrl);
    }

    @PostMapping("deleteFile")
    public void deleteFile(String fileUrl) {
        fcw.deleteFile(fileUrl);
    }


}
