package com.example.goodsmanage.mapper;

import org.apache.ibatis.annotations.Param;

import com.example.goodsmanage.model.FileModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* 针对表【FILES】的数据库操作Mapper
*/

@Mapper
public interface FileMapper extends BaseMapper<FileModel> {

    FileModel findOneByFid(@Param("fid") Integer fid);  // 查找fid符合的行

    FileModel findOneByHash(@Param("hash") String hash);  // 查找hash符合的行

    FileModel findOneByFidAndHash(@Param("fid") Integer fid, @Param("hash") String hash);  // 查找fid和hash都符合的行

    void insertAll(FileModel fileModel);  // 插入一个数据

}




