package com.example.goodsmanage.entity;


import com.example.goodsmanage.model.FileModel;
import com.example.goodsmanage.model.Goods;
import com.example.goodsmanage.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

/**
 * 用来传递给前端页面显示商品信息的ViewObject视图对象
 */


@Data
@NoArgsConstructor  // 无参构造方法，允许建立无内容的对象
public class GoodsVO {  // 用来向页面传递Goods信息的视图对象(View Object,VO)

    // GoodsVO既要显示商品信息，还要显示商品图，以及创建和修改用户的信息，所以传入多个对象取出需要的数据
    public GoodsVO(Goods goods, FileModel fileModel, User createUser, User lastModifiedUser){
        if(goods.getName() != null){
            this.gid = goods.getGid();
            this.name = goods.getName();
            this.imgFid = fileModel.getFid();
            this.imgUrl = "/file/fid/" + fileModel.getFid();
            this.price = goods.getPrice();
            this.details = goods.getDetails();
            this.detailsType = goods.getDetailsType();
            this.createUserSid = createUser.getSid();
            this.createUserUsername = createUser.getUsername();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.lastModified = simpleDateFormat.format(goods.getLastModified());
            this.inventory = goods.getInventory();
            this.classification = goods.getClassification();
            if(goods.getStatus().equals("up")){
                this.status = "上架";
            }else if(goods.getStatus().equals("down")){
                this.status = "下架";
            }
            this.lastModifiedUserSid = lastModifiedUser.getSid();
            this.lastModifiedUserUsername = lastModifiedUser.getUsername();
        }
    }
    private Integer gid;
    private String name;
    private String imgUrl;
    private Integer imgFid;
    private Double price;
    private String details;
    private String detailsType;
    private Integer createUserSid;
    private String createUserUsername;
    private String lastModified;
    private Integer inventory;
    private String classification;
    private String status;
    private Integer lastModifiedUserSid;
    private String lastModifiedUserUsername;

}