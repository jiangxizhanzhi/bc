package com.cyberpeace.situation.controller;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cs.controller.CommonController;
import com.cyberpeace.cs.controller.req.FileCommonUploadReq;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DashboardTemplatePageRes;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.dao.entity.DashboardComponents;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;
import com.cyberpeace.situation.service.DashboardTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.binary.Base64;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.Map;

/**
 * @Author xuxz
 * @Date 10/11/2022 上午11:58
 */

@Api(tags = "态势模板")
@RestController
@RequestMapping("dashboard/template/")
public class DashboardTemplateController {

    @Autowired
    private DashboardTemplateService dashboardTemplateService;

    @Autowired
    private CommonController commonController;

    @ApiOperation("新增态势模板")
    @PostMapping()
    public BaseRsp<String> createDashboardTemplate(@RequestBody @Validated(Create.class) DashBoardTemplateReq dashBoardTemplateReq) {
        return new BaseRsp<>(dashboardTemplateService.createDashboardTemplate(dashBoardTemplateReq));
    }

    @ApiOperation("修改态势模板")
    @PatchMapping("")
    public BaseRsp<String> updateDashboardTemplate(@RequestBody @Validated(Update.class) DashBoardTemplateReq dashBoardTemplateReq) {
        return new BaseRsp<>(dashboardTemplateService.updateDashboardTemplate(dashBoardTemplateReq));
    }

    @ApiOperation("获取改态势模板详情")
    @GetMapping("")
    public BaseRsp<DashboardTemplateRes> getDashboardTemplateInfo(@RequestParam("resource_id") @ApiParam(required = true, name = "resource_id", value = "资源id")
                                                                          String resourceId,
                                                                  @RequestParam(value = "type", required = false) @ApiParam(name = "type", value = "视角类型")
                                                                          String type) {
        return new BaseRsp<>(dashboardTemplateService.getDashboardTemplateInfo(resourceId, type, Boolean.FALSE));
    }

    @ApiOperation("批量删除改态势模板")
    @DeleteMapping("")
    public BaseRsp<Integer> deleteDashboardTemplate(@RequestBody @Validated DashboardTemplateDelReq dashboardTemplateDelReq) {
        return new BaseRsp<>(dashboardTemplateService.deleteDashboardTemplate(dashboardTemplateDelReq.getDashboardTemplateIdList()));
    }

    @ApiOperation("获取改态势模板列表")
    @GetMapping("list")
    public BaseRsp<ListRsp<DashboardTemplatePageRes>> getDashboardTemplatePage(DashboardTemplatePageReq dashboardTemplatePageReq) {
        return new BaseRsp<>(dashboardTemplateService.getDashboardTemplatePage(dashboardTemplatePageReq));
    }

    @ApiOperation("获取组件类型")
    @GetMapping("type/list")
    public BaseRsp<Map<Integer, String>> getComponentType() {
        return new BaseRsp<>(DashboardComponents.getComponentType());
    }

    @ApiOperation("获取模板等级列表")
    @GetMapping("level/list")
    public BaseRsp<Map<Integer, String>> getDashboardTemplateLevel() {
        return new BaseRsp<>(DashboardTemplate.getDashboardTemplateLevel());
    }

    @ApiOperation("获取echarts数据")
    @GetMapping("option/data")
    public BaseRsp<JSONArray> getEchartsOption(ComponentsDataReq componentsDataReq) {
        return new BaseRsp<>(dashboardTemplateService.getComponentsEchartsData(componentsDataReq));
    }

    @ApiOperation("Base64截图上传")
    @PostMapping("upload/image")
    public BaseRsp<AttachmentBo> uploadBackgroundImage(@RequestBody @Validated ImageReq imageReq) {
        String base64 = imageReq.getBase64();
        if (base64.contains("data:image")) {
            base64 = base64.substring(base64.indexOf(",") + 1);
        }
        base64 = base64.replace("\r\n", "");
        //创建文件目录
        String prefix = ".png";
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        InputStream input = null;
        OutputStream os = null;
        try {
            File file = File.createTempFile(IDGeneratorUtil.getID(), prefix);
            byte[] bytes = Base64.decodeBase64(base64);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            FileItem item = new DiskFileItemFactory().createItem("file"
                    , MediaType.MULTIPART_FORM_DATA_VALUE
                    , true
                    , file.getName());

            input = new FileInputStream(file);
            os = item.getOutputStream();
            // 流转移
            IOUtils.copy(input, os);
            CommonsMultipartFile commonsMultipartFile = new CommonsMultipartFile(item);
            FileCommonUploadReq fileCommonUploadReq = new FileCommonUploadReq();
            fileCommonUploadReq.setFile(commonsMultipartFile);
            BaseRsp<AttachmentBo> attachmentBoBaseRsp = commonController.imageUpload(fileCommonUploadReq);
            //删除图片
            file.delete();
            return attachmentBoBaseRsp;
        } catch (Exception e) {
            throw new BusinessException(SituationErrorCode.RELEASE_REPEAT_ERROR);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
