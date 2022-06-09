package com.example.goodsmanage.controller;

import com.example.goodsmanage.Utils;
import com.example.goodsmanage.model.FileModel;
import com.example.goodsmanage.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * /file/**页面的控制器类
 * 包含文件上传和获取的类(获取用于页面显示)
 */

@Controller
@RequestMapping("/file")
@Slf4j
public class FileController {
    final private FileService fileService;

    @Autowired  //自动装配Service
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @ResponseBody  // 设定这个方法不返回html模板，直接返回数据
    @PostMapping({"/upload"})  // 上传文件
    public Object uploadFile(@RequestParam("file") MultipartFile file) {
        String hash;
        Integer fid;
        try {
            hash = Utils.md5HashCode(file.getInputStream());  // 计算上传文件的hash值，每个文件hash值不同
            FileModel fileModel = fileService.selectOneFile(null, hash);  // 根据哈希值查找数据库有没有以前上传过，防止重复上传文件
            if (fileModel == null) {
                fid = fileService.insertFile(hash, file.getBytes());  // 查询无结果，把文件插入数据库
            } else {
                return fileModel.getFid();  // 数据库本来有这个文件了，返回已有文件的fid
            }
            return fid; // 返回刚插入数据库的图像的fid
        } catch (IOException e) {
            log.error(e.toString()); // 把错误信息输出了
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @GetMapping({"/hash/{hash}", "/fid/{fid}"})  // 根据hash或者fid查找图片
    public Object getFile(@PathVariable(value = "hash", required = false) String hash,
                          @PathVariable(value = "fid", required = false) Integer fid) {
        FileModel fileModel = fileService.selectOneFile(fid, hash);
        return ResponseEntity
                .ok()  // 状态200 OK
                .contentType(MediaType.IMAGE_JPEG) // 设定Content-Type(网络请求的内容格式的头部信息)为Image/JPEG(也就是JPEG图片)
                .body(fileModel.getFileBlob());  // 返回主体为数据库查询出的二进制内容(图片内容)
    }
}
