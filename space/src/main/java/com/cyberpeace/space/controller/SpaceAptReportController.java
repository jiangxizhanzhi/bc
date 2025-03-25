package com.cyberpeace.space.controller;

import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.oj.course.controller.req.LoadCourseReq;
import com.cyberpeace.space.controller.req.SpaceAptReportPageReq;
import com.cyberpeace.space.dao.entity.SpaceAptReport;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceAptReportService;
import com.cyberpeace.space.service.context.rule.SpaceAptReportBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.util.List;

@Api(tags = "知识库-理论知识库-APT报告")
@RestController
@RequestMapping("/space/theory/space_apt_report")
@RequiresAuthentication
@Slf4j
public class SpaceAptReportController extends SpaceControllerBase<SpaceAptReportService, SpaceAptReport, SpaceAptReportBo, SpaceAptReportPageReq> {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private UtilService utilService;

    @ApiOperation("数据导出")
    @GetMapping("/batch_dump")
    public BaseRsp<Boolean> exportReport(
            @ApiParam(name = "ids",
                    value = "课程编号") @RequestParam("ids") @NotEmpty List<String> ids,
            HttpServletResponse response) {
        String zipPath = baseService.dump(ids);
        FileUtil.setResponse(new File(zipPath), true, response);
        return new BaseRsp(Boolean.TRUE);
    }

    /**
     * 导入
     *
     * @param loadCourseReq 加载课程请求体
     * @return {@code BaseRsp<Boolean> }
     * @throws
     * @see LoadCourseReq
     * @see BaseRsp<Boolean>
     */
    @ApiOperation("数据导入")
    @PostMapping("/batch_load")
    public BaseRsp<Boolean> importCourse(@Validated LoadCourseReq loadCourseReq) {
        FileUtil.validateSuffix(loadCourseReq.getFile(), FileSunffixConstant.ZIP.getDesc());

        AttachmentBo attachmentBo = attachmentService.uploadFile(AttachmentPath.OTHER.getCode(),
                false, loadCourseReq.getFile(), true);
        String realPath = utilService.getRealPath(attachmentBo.getFilePath());
        Boolean result = baseService.loadReport(realPath);
        return new BaseRsp(result);

    }

}
