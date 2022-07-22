package com.cyberpeace.situation.controller;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.model.ws.WsAttackSchedule;
import com.cyberpeace.situation.service.DynamicSchemeService;
import com.cyberpeace.situation.service.SceneConfigService;
import com.cyberpeace.situation.service.impl.DynamicSchemeServiceImpl;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Api(tags = "态势编排")
@RestController
@Slf4j
@RequestMapping("/situation/control")
public class SituationControlController {

    @Autowired
    private DynamicSchemeService dynamicSchemeService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private SceneConfigService sceneConfigService;

    @ApiOperation("新增动效方案")
    @PostMapping("/scheme")
    public BaseRsp<String> addDynamicEffectScheme(@RequestBody @Validated AddDynamicSchemeReq addDynamicSchemeReq){
        dynamicSchemeService.validDynamicSchemeName(addDynamicSchemeReq);
        String resourceId = dynamicSchemeService.addDynamicScheme(addDynamicSchemeReq);
        return new BaseRsp<>(resourceId);
    }

    @ApiOperation("查看动效方案")
    @GetMapping("/scheme")
    public BaseRsp<DynamicSchemeRes> getDynamicEffectScheme(@ApiParam("动效方案id")String resourceId){
        DynamicSchemeRes dynamicSchemeRes = dynamicSchemeService.getDynamicSchemeById(resourceId);
        return new BaseRsp<>(dynamicSchemeRes);
    }

    @ApiOperation("更新动效方案")
    @PatchMapping("/scheme")
    public BaseRsp<DynamicSchemeReq> updateDynamicEffectScheme(@RequestBody @Validated DynamicSchemeReq dynamicSchemeReq){
        dynamicSchemeService.validStage(dynamicSchemeReq);
        DynamicSchemeReq dynamicSchemeReq1 = dynamicSchemeService.updateDynamicScheme(dynamicSchemeReq);
        return new BaseRsp<>(dynamicSchemeReq1);
    }

    @ApiOperation("删除动效方案")
    @DeleteMapping("/scheme/{resource_id}")
    public BaseRsp<?> deleteDynamicEffectScheme(@PathVariable("resource_id")String dynamicSchemeId){
        dynamicSchemeService.deleteDynamicScheme(dynamicSchemeId);
        return new BaseRsp<>();
    }

    @ApiOperation("所有动效方案名称")
    @GetMapping("/scheme_names")
    public BaseRsp<List<DynamicSchemeNameRes>> getAllDynamicName(@ApiParam("场景id")@Validated @NotBlank String sceneId){
        List<DynamicSchemeNameRes> names = dynamicSchemeService.getAllDynamicName(sceneId);
        return new BaseRsp<>(names);
    }

    @ApiOperation("返回所有选择项")
    @GetMapping("/option")
    public BaseRsp<OptionsRes> getAllOption(String sceneId){
        OptionsRes optionsRes = new OptionsRes();
        dynamicSchemeService.getLocalOption(optionsRes);
        dynamicSchemeService.getRemoteOption(optionsRes,sceneId);
        return new BaseRsp<>(optionsRes);
    }

    @ApiOperation("运行单个动效")
    @PostMapping("/run_attack_message")
    public BaseRsp<?> runAttackMessage(@RequestBody @Validated RunAttackMessageReq runAttackMessageReq){
        AttackMessageReq attackMessageBo = BeanUtils.copy(runAttackMessageReq, AttackMessageReq.class);
        dynamicSchemeService.clearMedia(runAttackMessageReq.getSceneId());
        dynamicSchemeService.clearRoomSchedule(runAttackMessageReq.getSceneId());
        String code = sceneConfigService.updateSceneCode(runAttackMessageReq.getSceneId());
        if(runAttackMessageReq.getType().equals(AttackType.MEDIA_ANIMATION.getKeyWord())){
            dynamicSchemeService.pushMediaToRoom(attackMessageBo, null, null, code);
        }else{
            dynamicSchemeService.pushAttackToRoom(attackMessageBo, null);
        }
        return new BaseRsp<>();
    }

    @ApiOperation("运行动效方案")
    @PostMapping("/run_dynamic_scheme")
    public BaseRsp<?> runDynamicScheme(@RequestBody @Validated DynamicSchemeReq dynamicSchemeReq){
        dynamicSchemeService.validStage(dynamicSchemeReq);
        dynamicSchemeService.pushDynamicSchemeToRoom(dynamicSchemeReq);
        return new BaseRsp<>();
    }

    @ApiOperation("上传媒体文件")
    @PostMapping("/upload_media")
    public BaseRsp<AttachmentBo> uploadMedia(@ApiParam(value = "上传文件",required = true) @RequestPart("file") MultipartFile multipartFile){
        String fileName = multipartFile.getOriginalFilename();
        FileUtil.validateFileSuffix(fileName, DynamicSchemeServiceImpl.PICTURE_TYPE+","+DynamicSchemeServiceImpl.VIDEO_TYPE);
        AttachmentBo attachmentBo = attachmentService.uploadFile(AttachmentPath.UPLOAD_SITUATION.getCode(), true, multipartFile, true);
        attachmentBo.setFilePath(attachmentBo.getFilePath());
        return new BaseRsp<>(attachmentBo);
    }

    @ApiOperation("获取当前进程")
    @GetMapping("/schedule")
    public BaseRsp<List<WsAttackSchedule>> getSchedule(@ApiParam("场景id")String sceneId){
        List<WsAttackSchedule> schedule = dynamicSchemeService.getSchedule(sceneId);
        return new BaseRsp<>(schedule);
    }

}
