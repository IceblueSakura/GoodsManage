package com.example.goodsmanage.services;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.goodsmanage.entity.GoodsVO;
import com.example.goodsmanage.model.Goods;

import java.util.List;


/**
 * 针对表【GOODS】的数据库操作Service接口
 */
public interface GoodsService extends IService<Goods> {
    /***
     * 添加一个商品
     * @param goods 添加的商品信息
     */
    void addGoods(Goods goods);

    /**
     * 批量删除商品
     *
     * @param goodsId 删除的商品ID
     */
    void deleteGoodsById(Integer goodsId);

    /**
     * 根据Goods对象内的数据更新数据库内容
     *
     * @param goods 需要修改的内容对象，必须有主键GID
     * @return 修改后的对象
     */
    void updateGoods(Goods goods);

    /**
     * 查询一个商品，参数二选一
     *
     * @param gid  查询的商品id
     * @param name 查询的商品名称
     * @return 查询到的商品对象
     */
    GoodsVO selectOneGoods(Integer gid, String name);


    /**
     * 获取全部商品
     *
     * @return 全部商品信息列表，包括create_userid,last_modified,last_modified_userid信息
     */
    List<GoodsVO> selectAllGoods();

    /**
     * 根据条件查询商品
     *
     * @param rangeBy      查询范围依据，如price
     * @param rangeLess    查询范围最小
     * @param rangeGreater 查询范围最大
     * @param orderBy      排序依据，如gid
     * @param isAsc        是否升序排列
     * @return 一个GoodsVO的列表
     */
    List<GoodsVO> selectByConditions(String rangeBy, Double rangeLess, Double rangeGreater, String orderBy, Boolean isAsc);  //asc升序 / desc降序

    /**
     * 查询所有分类
     * @return 分类String List
     */
    List<String> selectClassification();

    /**
     * 根据分类查找所有商品
     * @param classification 分类名
     * @return 商品List
     */
    List<GoodsVO> selectAllByClassification(String classification);

}
