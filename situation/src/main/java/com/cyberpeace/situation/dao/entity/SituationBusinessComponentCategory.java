package com.cyberpeace.situation.dao.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**态势业务组件类别
 *
 * @TableName situation_business_component_category
 @author dell
 @date 2023/06/20
 */
@Data
public class SituationBusinessComponentCategory extends BaseTable implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty("资源id")
    @TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
    private String resourceId;

    /**
     * 组件类别名称
     */
    private String name;
}