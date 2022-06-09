package com.example.goodsmanage.entity;

import com.example.goodsmanage.model.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用来接收上传商品的实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsUpload {
    /**
     * 把上传接收到的数据转换到Goods对象，方便进行数据库操作。
     * 在新建商品和修改商品时都会把接收到的数据转换为这个对象
     * @param lastModifiedUserSid 最后修改用户的sid
     * @param createUserSid 创建用户的sid，如果是更新就不用传入(传进来也会忽略掉,dao里设置的更新方法不会更新创建用户字段)
     * @return 返回转换好的Goods对象，可以直接传入dao相应的方法来进行增加或更新
     */
    public Goods convertToGoods(Integer lastModifiedUserSid,Integer createUserSid){
        String tmp;
        if(this.status.equals("上架")){  // 根据传入的"上架"还是"下架"判断数据库的status是插入up还是down，原因还是数据库不好存储中文
            tmp = "up";
        }else if (this.status.equals("下架")){
            tmp = "down";
        }else{
            tmp = "none";
        }
        // gid是数据库管理不能更新，
        return new Goods(this.gid,this.name,this.imgFid,this.price,this.details,this.detailsType,createUserSid,new Date(),this.inventory,this.classification,tmp,lastModifiedUserSid);
   }

    /**
     * 转换到Goods(convertToGoods)的重载(名字一样,参数必须不同,返回类型也可以不同)方法，省了一个写null的机会
     * @param lastModifiedUserSid 最后修改用户的sid
     * @return 返回转换好的Goods对象，可以直接传入dao相应的方法来进行增加或更新
     */
    public Goods convertToGoods(Integer lastModifiedUserSid){
        return convertToGoods(lastModifiedUserSid,null);
    }

    private Integer gid;
    private String name;
    private Integer imgFid;
    private Double price;
    private String classification;
    private Integer inventory;
    private String status;
    private String details;
    private String detailsType;

}
