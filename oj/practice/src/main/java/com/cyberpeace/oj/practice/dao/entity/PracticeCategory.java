package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PracticeCategory {

    // 资源ID
    private String resourceId;

    // 名称
    private String name;

    @JsonIgnore
    private Integer id;

    // 状态
    @JsonIgnore
    private Integer status;

    @JsonIgnore
    private Integer practiceType;

}
