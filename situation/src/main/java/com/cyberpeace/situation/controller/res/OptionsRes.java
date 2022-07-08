package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.constant.Color;
import com.cyberpeace.situation.constant.LineType;
import com.cyberpeace.situation.constant.ws.AttackType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OptionsRes implements Serializable {

    @ApiModelProperty("动效类型")
    private List<AttackType> attackTypeList;

    @ApiModelProperty("线型")
    private List<LineType> lineTypeList;

    @ApiModelProperty("颜色")
    private List<Color> colorList;

}
