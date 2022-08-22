package com.cyberpeace.oj.ojbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.ojbase.service.OJBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags = "靶场公用接口")
@RestController
@Slf4j
@RequestMapping("/oj/common")
@RequiresAuthentication
public class OjBaseController {

    @Autowired
    private OJBaseService ojBaseService;


	@ApiOperation("资管课程导出")
	@GetMapping("/rm/dump/course")
    public BaseRsp<String> RMCourseDump(@ApiParam(name = "resource_id", value = "资源id", required = true)
                                            @RequestParam(value = "resource_id") String resource_id,
                                        @ApiParam(name = "is_dump_topo", value = "是否下载拓扑数据", required = true)
                                            @RequestParam(value = "is_dump_topo") Integer is_dump_topo) throws IOException {

        BaseRsp<String> rsp = new BaseRsp<>();
        if (null == resource_id){
            rsp.setMessage("resource_id is needed");
            return rsp;
        }
        boolean dump_topo = null != is_dump_topo && is_dump_topo == 1;
        // 执行课程数据导出
        String result = ojBaseService.rMCourseDump(resource_id,dump_topo);
        rsp.setData(result);
        rsp.setMessage("resource dump successfully!");
        return rsp;
    }

}
