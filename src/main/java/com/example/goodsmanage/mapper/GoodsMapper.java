package com.example.goodsmanage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.goodsmanage.model.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 * 针对表【GOODS】的数据库操作Mapper
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    Goods findByGid(@Param("gid") int gid);

    Goods findByName(@Param("name") String name);

    Goods findOneByGidAndName(@Param("gid") Integer gid, @Param("name") String name);

    List<Goods> findAll();  // 查找所有商品

    void updateAll(Goods goods);

    List<String> findClassification();

    List<Goods> findAllByClassification(String classification);
}




