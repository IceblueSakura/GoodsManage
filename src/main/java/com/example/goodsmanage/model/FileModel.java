package com.example.goodsmanage.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * FILES表的Model
 * @TableName FILES
 */
@TableName(value ="FILES")
@Data
public class FileModel implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer fid;
    /**
     * 
     */
    private String hash;

    /**
     * 
     */
    private byte[] fileBlob;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}