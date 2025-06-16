package com.cyberpeace.space.controller;

import com.cyberpeace.common.constant.CallType;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.oj.course.controller.req.LoadCourseReq;
import com.cyberpeace.space.controller.req.SpaceAptReportPageReq;
import com.cyberpeace.space.dao.entity.SpaceAptReport;
import com.cyberpeace.space.dao.entity.SpaceCallCount;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceAptReportService;
import com.cyberpeace.space.service.SpaceCallCountService;
import com.cyberpeace.space.service.context.rule.SpaceAptReportBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.util.List;

/**
 * 空间容易报告Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 概述 
 * 这段代码定义了一个名为 `SpaceAptReportController` 的 Spring MVC 控制器类，用于处理与空间APT报告相关的 HTTP 请求。该类继承自 `SpaceControllerBase`，并实现了两个核心功能：**数据导出**和**数据导入**。 
 * --- 
 * #### 主要功能 
 * 1. **数据导出** 
 * - **方法名称**: `exportReport` 
 * - **HTTP 方法**: GET 
 * - **路径**: `/space/theory/space_apt_report/batch_dump` 
 * - **功能**: 根据传入的 ID 列表（`ids`），调用 `baseService.dump(ids)` 方法生成压缩文件，并通过 `FileUtil.setResponse()` 将文件流写入 HTTP 响应中。 
 * - **参数**: 
 * - `ids`: 必填，表示需要导出的报告的 ID 列表。 
 * - `response`: Spring 提供的 HTTP 响应对象。 
 * - **返回值**: 返回一个包含布尔值的通用响应对象 `BaseRsp<Boolean>`，指示操作是否成功。 
 * 2. **数据导入** 
 * - **方法名称**: `importCourse` 
 * - **HTTP 方法**: POST 
 * - **路径**: `/space/theory/space_apt_report/batch_load` 
 * - **功能**: 接收上传的 ZIP 文件，验证其后缀合法性，将其存储到指定路径，然后调用 `baseService.loadReport(realPath)` 方法加载报告。 
 * - **参数**: 
 * - `loadCourseReq`: 包含上传文件的请求体对象，类型为 `LoadCourseReq`。 
 * - **返回值**: 返回一个包含布尔值的通用响应对象 `BaseRsp<Boolean>`，指示操作是否成功。 
 * --- 
 * #### 关键点 
 * 1. **依赖注入** 
 * - 使用了 `@Autowired` 注解，将 `AttachmentService` 和 `UtilService` 注入到控制器中。 
 * - 这些服务提供了附件管理和工具支持功能。 
 * 2. **Swagger 文档注解** 
 * - 使用了 `@Api` 和 `@ApiOperation` 注解，生成了清晰的接口文档，便于前端开发者理解接口用途。 
 * 3. **安全控制** 
 * - 使用了 `@RequiresAuthentication` 注解，确保只有经过认证的用户才能访问这些接口。 
 * 4. **异常处理** 
 * - 虽然代码未显式处理异常，但依赖的服务（如 `FileUtil` 和 `attachmentService`）可能已经内置了必要的异常捕获机制。 
 * 5. **继承关系** 
 * - 继承自 `SpaceControllerBase`，表明该类复用了父类的一些通用逻辑（如 `baseService` 的操作）。 
 * --- 
 * #### 总结 
 * 此代码实现了一个典型的前后端交互场景，提供了数据导出和导入的功能。它结合了 Spring MVC、Swagger 文档注解以及文件操作工具类，体现了良好的模块化设计。同时，通过继承父类和依赖注入的方式，减少了重复代码，提升了代码的可维护性。 
**/
@Api(tags = "知识库-理论知识库-APT报告")
@RestController
@RequestMapping("/space/theory/space_apt_report")
@RequiresAuthentication
@Slf4j
public class SpaceAptReportController extends SpaceControllerBase<SpaceAptReportService, SpaceAptReport, SpaceAptReportBo, SpaceAptReportPageReq> {

    /**
     * 附件Service
     *
     * @see AttachmentService
     */
    @Autowired
    private AttachmentService attachmentService;

    /**
     * utilService
     *
     * @see UtilService
     */
    @Autowired
    private UtilService utilService;

    private SpaceCallCountService spaceCallCountService;

    /**
     * 导出报告
     *
     * @param ids      id列表
     * @param response 响应
     * @return base rsp< 布尔值>
     * @throws
     * @see List<String>
     * @see HttpServletResponse
     * @see BaseRsp<Boolean>
     */
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
    @Override
    public void checkValue(SpaceAptReport entity){
        if (!StringUtils.isEmpty(entity.getAptOrg())){
            CrCommonValidator.validateNameLen(entity.getAptOrg(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
        }
        if (!StringUtils.isEmpty(entity.getPublicCompany())) {
            CrCommonValidator.validateNameLen(entity.getPublicCompany(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
        }
    }

    @Override
    public void detailAddCallTypeNum(){
        spaceCallCountService.addCount(CallType.THEORY);
    }
}

