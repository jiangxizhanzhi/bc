package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.oj.practice.dao.constants.PracticeType;
import com.cyberpeace.oj.practice.dao.constants.SceneShare;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@TableName("practice_operation")
public class PracticeOperation extends PracticeBase {

    @JsonIgnore
    @TableField(exist = false)
    private Integer practiceType = PracticeType.OPERATION.getValue();

    /**
     * 附件
     */
    private String file;

    /**
     * 附件名称
     */
    private String fileName;

    /**
     * 题解
     */
    private String writeup;

    /**
     * 是否公开题解
     */
    private boolean isPublicWriteup = false;

    /**
     * 动态答题环境(使用场景)
     */
    private boolean isDynamicEnv;

    /**
     * 关联场景管理ID
     */
    private String ojSceneConfig;

    /**
     * 共享模式 (拥有场景时使用)
     */
    private Integer shareMode = SceneShare.PRIVATE.getValue();

    /**
     * 是否使用动态FLAG (拥有场景时使用)
     */
    private boolean isDynamicFlag = false;

    /**
     * 场景存活时间 单位/h (拥有场景时使用)
     */
    private Integer destroyDelay = 2;
}
