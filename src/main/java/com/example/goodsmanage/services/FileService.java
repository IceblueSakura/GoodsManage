package com.example.goodsmanage.services;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.goodsmanage.model.FileModel;

/**
* 针对表【FILE_HASH】的数据库操作Service接口
*/
public interface FileService extends IService<FileModel> {

    /**
     * 根据两个参数（可以只传入一个）获取文件资源
     * @param fid 主键
     * @param hash 哈希值
     * @return FileHash对象
     */
    FileModel selectOneFile(Integer fid, String hash);

    /**
     * 插入文件
     * @param hash 文件哈希值
     * @param blob 文件二进制内容
     * @return 返回插入后数据的fid主键
     */
    Integer insertFile(String hash,byte[] blob);

}
