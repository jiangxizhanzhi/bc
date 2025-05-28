package com.cyberpeace.space.controller;

import com.cyberpeace.common.constant.JsonFileResponseEnum;
import com.cyberpeace.common.util.FileUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceTacticDefenseSystem;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.SpaceTacticDefenseSystemService;
import com.cyberpeace.space.service.context.algorithm.SpaceTacticDefenseSystemBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * 空间战法防御系统Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "战法库-防御技战法体系")
@RestController
@RequestMapping("/space/tactic/system")
//@RequiresAuthentication
@Slf4j
public class SpaceTacticDefenseSystemController extends SpaceControllerBase<SpaceTacticDefenseSystemService, SpaceTacticDefenseSystem, SpaceTacticDefenseSystemBo, SpacePageReqBase> {



    @ApiOperation("ATT&CK框架")
    @GetMapping("/attack_frame")
    public ResponseEntity<String> getAttackFrame() throws IOException {
        File json = ResourceUtils.getFile(JsonFileResponseEnum.ATTACK.getFilePath());
        String content = FileUtil.getContent(json);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(content);
    }

    @ApiOperation("Engage框架")
    @GetMapping("/engage_frame")
    public ResponseEntity<String> getEngageFrame() throws IOException {
        File json = ResourceUtils.getFile(JsonFileResponseEnum.ENGAGE.getFilePath());
        String content = FileUtil.getContent(json);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(content);
    }

    @ApiOperation("D3FEND框架")
    @GetMapping("/defend_frame")
    public ResponseEntity<String> getDefendFrame() throws IOException {
        File json = ResourceUtils.getFile(JsonFileResponseEnum.DEFEND.getFilePath());
        String content = FileUtil.getContent(json);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(content);
    }
}
