package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.oj.ojbase.controller.req.RMImportReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 马币实践导入请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RMImportReq
 * @since 1.0.0
 */
@Data
public class RmPracticeImportReq extends RMImportReq {

    /**
     * key=实操题或习题名称 value=topoId
     *
     * @see Map<String, String>
     */
    @ApiModelProperty("资源和topo映射关系")
    private Map<String, String> envReqs;

}
