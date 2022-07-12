package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.model.AreaInfo;
import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.ElementType;
import com.cyberpeace.situation.model.LocalType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class OptionsRes implements Serializable {

    @ApiModelProperty("动效类型")
    private List<LocalType> attackTypeList;

    @ApiModelProperty("线型")
    private List<LocalType> lineTypeList;

    @ApiModelProperty("颜色")
    private List<LocalType> colorList;

    @ApiModelProperty("节点id和名称")
    private List<ElementType> elementList;

    @ApiModelProperty("区域id和名称")
    private List<ElementType> areaList;

}
