package com.cyberpeace.oj.ojbase.controller;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.ResourceDumpConstant;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.ojbase.service.OJBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Api(tags = "靶场公用接口")
@RestController
@Slf4j
@RequestMapping("/oj/common")
@RequiresAuthentication
public class OjBaseController {

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private OJBaseService ojBaseService;


	@ApiOperation("资管课程导出")
	@GetMapping("/rm/dump/course")
	public BaseRsp<String> RMCourseDump(HttpServletResponse response) {

        // TODO 存放路径
        String path = "";
        UserInfo userInfo = remoteUserService.getById(ResourceImportConstant.SUPER_ADMIN_HASH);
        AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
        // 执行课程数据导出
        File result = ojBaseService.rMCourseDump(path, authContext);
        FileUtil.setResponse(result, true, response);
        BaseRsp<String> rsp = new BaseRsp<>();
        rsp.setData(ResourceDumpConstant.DUMP_ALL_FILE_STORE_PATH +File.separator + ResourceDumpConstant.DUMP_ALL_COURSE_FILE_STORE_PATH + ".zip");
        return rsp;
    }

}
