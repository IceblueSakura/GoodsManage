package com.example.goodsmanage.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.goodsmanage.mapper.FileMapper;
import com.example.goodsmanage.model.FileModel;
import com.example.goodsmanage.services.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
* 针对表【FILES】的数据库操作Service接口的实现
*/
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileModel>
    implements FileService {

    final private FileMapper fileMapper;
    @Autowired
    public FileServiceImpl(FileMapper fileMapper){
        this.fileMapper = fileMapper;
    }

    /**
     * 根据两个参数（可以只传入一个）获取文件资源
     *
     * @param fid  主键
     * @param hash 哈希值
     * @return FileHash对象
     */
    @Override
    public FileModel selectOneFile(Integer fid, String hash) {
        if(fid!=null&&hash!=null){
            return fileMapper.findOneByFidAndHash(fid,hash);
        }else if(fid!=null){
            return fileMapper.findOneByFid(fid);
        }else if(hash!=null){
            return fileMapper.findOneByHash(hash);
        }
        return null;
    }

    /**
     * 插入文件
     *
     * @param hash 文件哈希值
     * @param blob 文件二进制内容
     */
    @Override
    public Integer insertFile(String hash, byte[] blob) {
        FileModel fileModel = new FileModel();
        fileModel.setHash(hash);
        fileModel.setFileBlob(blob);
        fileMapper.insertAll(fileModel);
        return fileMapper.findOneByHash(hash).getFid();
    }
}




