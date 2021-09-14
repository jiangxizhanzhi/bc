package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.oj.practice.dao.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
@TableName("practice_category")
public class PracticeCategory extends BaseTable {

    /**
     * 资源ID
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 节点编码, 基于父级节点: p1(父节点) -> p1|c1(子节点)
     */
    @JsonIgnore
    private String code;

    /**
     * 子级节点
     */
    @TableField(exist = false)
    private List<PracticeCategory> children;

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
