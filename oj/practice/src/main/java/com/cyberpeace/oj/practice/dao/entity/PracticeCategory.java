package com.cyberpeace.oj.practice.dao.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@TableName("practice_theory_category")
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
