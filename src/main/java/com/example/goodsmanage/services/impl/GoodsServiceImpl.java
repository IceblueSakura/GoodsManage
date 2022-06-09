package com.example.goodsmanage.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.goodsmanage.entity.GoodsVO;
import com.example.goodsmanage.mapper.FileMapper;
import com.example.goodsmanage.mapper.UserMapper;
import com.example.goodsmanage.model.Goods;
import com.example.goodsmanage.mapper.GoodsMapper;
import com.example.goodsmanage.services.GoodsService;
;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 针对表【GOODS】的数据库操作Service接口的实现
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
        implements GoodsService {

    final private GoodsMapper goodsMapper;
    final private UserMapper userMapper;
    final private FileMapper fileMapper;

    @Autowired
    public GoodsServiceImpl(GoodsMapper goodsMapper, UserMapper userMapper, FileMapper fileMapper) {
        this.goodsMapper = goodsMapper;
        this.userMapper = userMapper;
        this.fileMapper = fileMapper;
    }

    /***
     * 添加一个商品
     * @param goods 添加的商品信息
     */
    @Override
    public void addGoods(Goods goods) {
        goodsMapper.insert(goods);
    }

    /**
     * 批量删除商品
     *
     * @param goodsId 批量删除的商品ID
     */
    @Override
    public void deleteGoodsById(Integer goodsId) {
        goodsMapper.deleteById(goodsId);
    }

    /**
     * 根据Goods对象内的数据更新数据库内容
     *
     * @param goods 需要修改的内容对象，必须有主键GID
     * @return 修改后的对象
     */
    @Override
    public void updateGoods(Goods goods) {
        goodsMapper.updateAll(goods);
    }

    /**
     * 查询一个商品，参数二选一
     *
     * @param gid  查询的商品id
     * @param name 查询的商品名称
     * @return
     */
    @Override
    public GoodsVO selectOneGoods(Integer gid, String name) {
        Goods goods = null;
        if (gid != null && name != null) {
            goods = goodsMapper.findOneByGidAndName(gid, name);
        } else if (gid != null) {  //
            goods = goodsMapper.findByGid(gid);
        } else if (name != null) {
            goods = goodsMapper.findByName(name);
        } else {  // gid和name都是null，没法查询就返回null
            return null;
        }
        if (goods == null) {  // 查询不到数据也null
            return null;
        }
        return new GoodsVO(
                goods,  // 商品本体
                fileMapper.findOneByFid(goods.getImgFid()),  // 商品图片
                userMapper.findBySid(goods.getCreateUserid()),  // 商品创建人
                userMapper.findBySid(goods.getLastModifiedUserid())  // 商品最后修改人
        );
    }

    /**
     * 获取全部商品
     *
     * @return 全部商品信息列表，包括create_userid,last_modified,last_modified_userid信息
     */
    @Override
    public List<GoodsVO> selectAllGoods() {
        List<Goods> goodsList = goodsMapper.findAll();
        List<GoodsVO> goodsVOList = new ArrayList<>();
        for (Goods goods : goodsList) {

            goodsVOList.add(new GoodsVO(
                    goods,  // 商品本体
                    fileMapper.findOneByFid(goods.getImgFid()),  // 商品图片
                    userMapper.findBySid(goods.getCreateUserid()),  // 商品创建人
                    userMapper.findBySid(goods.getLastModifiedUserid())  // 商品最后修改人
            ));
        }
        return goodsVOList;
    }

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
    @Override
    public List<GoodsVO> selectByConditions(String rangeBy, Double rangeLess, Double rangeGreater, String orderBy, Boolean isAsc) {
        boolean orderCondition = orderBy != null;  // 如果orderBy条目有内容，则这个变量为true.这个变量决定wrapper是否进行排序
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (rangeBy == null) {
            queryWrapper.orderBy(orderCondition, isAsc, orderBy);
        } else {
            queryWrapper
                    .le(rangeBy, rangeGreater)
                    .ge(rangeBy, rangeLess)
                    .orderBy(orderCondition, isAsc, orderBy);
        }

        List<GoodsVO> goodsVOList = new ArrayList<>();
        for (Goods goods : goodsMapper.selectList(queryWrapper)) {
            goodsVOList.add(new GoodsVO(
                    goods,  // 商品本体
                    fileMapper.findOneByFid(goods.getImgFid()),  // 商品图片
                    userMapper.findBySid(goods.getCreateUserid()),  // 商品创建人
                    userMapper.findBySid(goods.getLastModifiedUserid())  // 商品最后修改人
            ));
        }
        return goodsVOList;
    }

    @Override
    public List<String> selectClassification() {
        return goodsMapper.findClassification();
    }

    @Override
    public List<GoodsVO> selectAllByClassification(String classification) {
        List<GoodsVO> goodsVOList = new ArrayList<>();
        for (Goods goods : goodsMapper.findAllByClassification(classification)) {
            goodsVOList.add(new GoodsVO(
                    goods,  // 商品本体
                    fileMapper.findOneByFid(goods.getImgFid()),  // 商品图片
                    userMapper.findBySid(goods.getCreateUserid()),  // 商品创建人
                    userMapper.findBySid(goods.getLastModifiedUserid())  // 商品最后修改人
            ));
        }
        return goodsVOList;
    }


}




