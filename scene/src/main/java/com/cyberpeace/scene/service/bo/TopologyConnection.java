package com.cyberpeace.scene.service.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/21 10:26
 * @desc :
 **/
@Data
public class TopologyConnection implements Serializable {

    private String id;

    private String name;

    private String source;

    private String target;

    private boolean unidirectional;

    private List<String> vertices;

    private String type;

    @TableField(value = "display_context")
    private String displayContext;
}
