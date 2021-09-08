package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.oj.practice.dao.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("practice_category")
public class PracticeCategory extends BaseTable implements Serializable {

    /**
     * 资源ID
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 父级分类, 非必填
     */
    private String parent;

    /**
     * 名称
     */
    private String name;

    @JsonIgnore
    private Integer id;

    /**
     * 状态
     */
    @JsonIgnore
    private Integer status = Status.NORMAL.getValue();

    /**
     * 练习类型
     */
    @JsonIgnore
    private Integer practiceType;

}
