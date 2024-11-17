package com.cyberpeace.integratation.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 镜像新增请求信息
 */
@Data
public class ImageByUuidsBo {

    /**
     * UUID
     */
    @NotEmpty
    @ApiModelProperty("UUID集合")
    private List<String> uuids;

}
