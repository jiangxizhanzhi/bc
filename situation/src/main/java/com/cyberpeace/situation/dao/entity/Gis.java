package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:00
 */
@Data
@TableName("situation_gis")
public class Gis extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    private Integer type;

    private String img;

    private String url;

}
