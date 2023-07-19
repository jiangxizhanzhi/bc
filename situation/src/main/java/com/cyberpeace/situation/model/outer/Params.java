package com.cyberpeace.situation.model.outer;

import com.cyberpeace.situation.model.outer.Param;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Params implements Serializable {

    @ApiModelProperty("参数列表")
    private List<Param> params;
}
