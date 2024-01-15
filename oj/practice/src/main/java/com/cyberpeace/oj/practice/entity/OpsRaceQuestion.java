package com.cyberpeace.oj.practice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ops_race_question")
public class OpsRaceQuestion {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    @TableField("`name`")
    private String name;
    private String desc;




}
