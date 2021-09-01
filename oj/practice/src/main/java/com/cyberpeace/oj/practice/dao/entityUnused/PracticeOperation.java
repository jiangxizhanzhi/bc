package com.cyberpeace.oj.practice.dao.entityUnused;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("实践操作练习")
public class PracticeOperation extends PracticeBase {

    @ApiModelProperty("附件")
    private String file;

    // 附件名称
    private String fileName;

    @ApiModelProperty("题解")
    private String writeup;

    // 题解是否公开
    private boolean isPublicWriteup;

    @ApiModelProperty("动态答题环境(使用场景)")
    private boolean isDynamicEnv;

    // 关联场景管理ID
    private String ojSceneConfig;

    @ApiModelProperty("共享模式")
    private Integer share_mode;

    @ApiModelProperty("是否使用动态FLAG")
    private boolean isDynamicFlag;

    @ApiModelProperty("场景存活时间")
    private Integer destroyDelay;
}
