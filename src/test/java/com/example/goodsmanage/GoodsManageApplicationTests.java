package com.example.goodsmanage;

import com.example.goodsmanage.entity.GoodsVO;
import com.example.goodsmanage.mapper.FileMapper;
import com.example.goodsmanage.mapper.GoodsMapper;
import com.example.goodsmanage.mapper.UserMapper;
import com.example.goodsmanage.model.FileModel;
import com.example.goodsmanage.services.GoodsService;
import com.example.goodsmanage.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 测试类，程序正常运行时不会执行
 */


@SpringBootTest
class GoodsManageApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsService goodsService;
    @Autowired
    UserService userService;
    @Autowired
    FileMapper fileMapper;

    @Test
    void contextLoads() {
    }
//    @Test
//    void insertTest(){
//        User user = new User(null,"test","123456",1,new Date(),"测试用户");
//        userMapper.insert(user);
//    }
//    @Test
//    void selectTestUserWrapper(){
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username","test");
//        User user = userMapper.selectOne(queryWrapper);
//        System.out.println(user);
//    }
//    @Test
//    void selectTestUseMapper(){
//        Goods goods = goodsMapper.findByName("第一件商品");
//        System.out.println(goods);
//    }
//    @Test
//    void updateTestUserObject(){
//        Goods goods = goodsMapper.findByGid(22);
//        goods.setDetails("这是修改过的第一件商品的详情");
//        goodsMapper.updateById(goods);
//    }
//    @Test
//    void updateTestUseWrapper(){
//        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("gid",22);
//        updateWrapper.set("details","这是使用Wrapper修改的商品信息");
//        goodsMapper.update(null,updateWrapper);
//    }
//    @Test
//    void deleteTestUseWrapper(){
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username","test");
//        userMapper.delete(queryWrapper);
//    }
//    @Test
//    void deleteTestUseObject(){
//        User user = userMapper.findByUsername("test");
//        userMapper.deleteById(user);
//    }
//    @Test
//    void deleteExceptionTest(){
//        // 数据不存在：org.mybatis.spring.MyBatisSystemException
//        // 违反约束条件：java.sql.SQLIntegrityConstraintViolationException
//        try{
//            User user = userMapper.findByUsername("admin");
//            userMapper.deleteById(user);
//        }catch (Exception e) {
//            System.out.println("删除失败！" + e);
//        }
//    }
//    @Test
//    void deleteObjectsTest(){
////        goodsMapper.deleteBatchIds()
//    }
//    @Test
//    void hashTest(){
//        try {
//            System.out.println(MD5Utils.md5HashCode("E:\\code\\Android\\GoodsManage\\src\\main\\resources\\static\\b21b103d317087cc90416d847111ab06.jpg"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//    @Test
//    void Test(){
//        UserDTO userDTO = userService.selectOneUser(1,null);
//        System.out.println(userDTO);
//    }
//    @Test
//    void selectNotFoundTest(){
//        System.out.println("使用Mapper查询： " + goodsMapper.findByGid(222));
//        System.out.println("使用Service查询： " + goodsService.selectOneGoods(111,null));
//    }
//    @Test
//    void tokenInfoGetTest(){
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiYWRtaW4iLCJleHAiOjE2MzkyOTM2MjMsInNpZCI6MX0.L7bHEvEQCT3r5d7i4u67YdjhjoHLhzNFyNHcFExHVIY";
//        TokenInfo tokenInfo = Utils.getInfoByToken(token);
//        System.out.println(tokenInfo);
//    }
//    @Test
//    void fileInsertTest(){
//        FileModel fileModel = new FileModel();
//        fileModel.setHash("aabbccdd");
//        fileModel.setFileBlob(fileMapper.findOneByFid(41).getFileBlob());
//        fileMapper.insertAll(fileModel);
//    }
//    @Test
//    void conditionsSelectTest(){
//        List<GoodsVO> goodsList = goodsService.selectByConditions("inventory",100.0,200.0,null,true);
//        System.out.println(goodsList.toString());
//    }
}
