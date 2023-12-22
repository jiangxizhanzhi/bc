package com.cyberpeace.scene.service.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 *  @author :   SN
 *  @date   :   2023/12/21 9:29
 *  @desc   :
**/
@Data
public class TopologyArea {

    private String id;

    private String position2;

    private String name;

    @TableField(value = "display_context")
    private String displayContext;
}
