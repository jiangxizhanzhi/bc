package com.cyberpeace.oj.practice.dao.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_finger_print")
public class FingerPrint {
    @TableId(type = IdType.ASSIGN_ID, value = "resource_id")
    private String resourceId;

    private String product;

    private String company;

    private String parentCategory;

    private String category;

    private String rules;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean deletedFlag;
}
