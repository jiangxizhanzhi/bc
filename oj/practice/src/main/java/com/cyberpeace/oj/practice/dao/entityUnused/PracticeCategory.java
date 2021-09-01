package com.cyberpeace.oj.practice.dao.entityUnused;

import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("练习分类")
public class PracticeCategory extends BaseTable {

    // 资源ID
    private String resourceId;

    // 状态
    private Integer status;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("练习类型")
    private Integer practiceType;

}
