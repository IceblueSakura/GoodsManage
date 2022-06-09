package com.example.goodsmanage.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GOODS表的Model
 * @TableName GOODS
 */
@TableName(value ="GOODS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer gid;

    /**
     * 
     */
    private String name;

    /**
     * 
     */

    private Integer imgFid;

    /**
     * 
     */
    private Double price;

    /**
     * 
     */
    private String details;

    /**
     * 
     */
    private String detailsType;

    /**
     * 
     */
    private Integer createUserid;

    /**
     * 
     */
    private Date lastModified;

    /**
     * 
     */
    private Integer inventory;

    /**
     * 
     */
    private String classification;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private Integer lastModifiedUserid;

    @TableField(exist = false)
    private static final Long serialVersionUID = 1L;
}