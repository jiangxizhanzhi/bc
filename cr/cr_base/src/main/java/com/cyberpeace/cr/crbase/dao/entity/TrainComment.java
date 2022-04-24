package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cr_train_comment")
public class TrainComment extends BaseTable {
    @JsonIgnore
    private Integer id;

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 发布内容
     */
    private String content;

    /**
     * 发布者id
     */
    private String userId;

    /**
     * 红蓝对抗、团队训练房间id或单兵训练业务模板id
     */
    private String trainId;

    /**
     * 训练类型，同{@link BusinessTemplate#businessType}
     */
    private Integer businessType;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String avatarUrl;
}
