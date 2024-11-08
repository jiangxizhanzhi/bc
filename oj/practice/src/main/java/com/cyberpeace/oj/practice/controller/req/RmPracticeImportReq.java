package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.oj.ojbase.controller.req.RMImportReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 马币实践进口请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class RmPracticeImportReq extends RMImportReq {

    /**
     * key=实操题或习题名称 value=topoId
     */
    @ApiModelProperty("资源和topo映射关系")
    private Map<String, String> envReqs;

}
