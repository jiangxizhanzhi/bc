package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("practice_exercise_practiceexercise")
public class PracticeOperation extends PracticeBase{

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
    private String officialWriteup;

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
     * 共享模式
     */
    private Integer share_mode;

    /**
     * 是否使用动态FLAG
     */
    private boolean isDynamicFlag = false;

    /**
     * 场景存活时间
     */
    private Integer destroyDelay = 2;
}
