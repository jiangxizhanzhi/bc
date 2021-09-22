package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.oj.practice.dao.constants.Difficulty;
import com.cyberpeace.oj.practice.dao.constants.Status;
import com.cyberpeace.oj.practice.service.bo.PracticeCategoryParent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PracticeBase extends BaseTable implements Serializable {

    @JsonIgnore
    private Integer id;

    /**
     * 状态
     */
    @JsonIgnore
    private Integer status = Status.NORMAL.getValue();

    /**
     * 资源ID
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    @ApiModelProperty("标识")
    private String resourceId;

    /**
     * 是否内建
     */
    @JsonIgnore
    private boolean builtin = false;

    /**
     * 公开
     */
    @TableField("public")
    private boolean isPublic = true;

    /**
     * 所属习题集
     */
    @ApiModelProperty("所属习题集")
    private String practiceSet;

    /**
     * 所属分类
     */
    @ApiModelProperty("所属分类")
    private String category;

    // 分类信息 TODO DAO可以引用service.bo?
    @TableField(exist = false)
    private PracticeCategoryParent categoryInfo;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty("题目内容")
    private String content;

    /**
     * 答案
     */
    @ApiModelProperty("答案, 不同题型记录形式不同. 例: 单选-A, 多选-A|B, 判断-A, 填空-[{\"weight\":40,\"value\":\"A|a\"}, {\"weight\":60,\"value\":\"B\"}], 简答-纯文本")
    private String answer;

    /**
     * 难度, 目前仅操作题使用
     */
    @ApiModelProperty("难度, 0-初级, 1-中级, 2-高级")
    private Integer difficulty = Difficulty.PRIMARY.getValue();

    /**
     * 知识点
     */
    @ApiModelProperty("知识点. 列表, json字符串形式")
    private String knowledge;

    /**
     * 分值
     */
    @ApiModelProperty("分值, 目前在基础题库中无实际作用, 可忽略.")
    private Float score = 0.0f;

    /**
     * 练习类型, 数据库无此字段, 子类覆写, 设置默认值
     */
    private Integer practiceType;

    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createUser;

}
