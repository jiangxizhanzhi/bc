/**
 * 该代码定义了一个名为 `SpaceTacticDefenseSystemController` 的 Spring MVC 控制器类，用于处理与“战法库-防御技战法体系”相关的 HTTP 请求。以下是代码的主要功能和结构分析： 
 * ### 主要功能： 
 * 1. **控制器功能**： 
 * - 提供了三个 GET 请求接口，分别用于获取 ATT&CK 框架、Engage 框架和 D3FEND 框架的数据。 
 * - 数据以 JSON 格式返回。 
 * 2. **文件读取逻辑**： 
 * - 使用 `ResourceUtils.getFile()` 方法加载指定路径的 JSON 文件。 
 * - 调用 `FileUtil.getContent()` 方法读取文件内容并返回给客户端。 
 * 3. **注解说明**： 
 * - `@RestController`：标识这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping`：定义了基础路径 `/space/tactic/system`。 
 * - `@ApiOperation`：为接口提供 Swagger 文档描述。 
 * - `@Slf4j`：引入日志功能（虽然未在代码中使用日志语句）。 
 * 4. **继承关系**： 
 * - 该类继承自 `SpaceControllerBase`，实现了通用的 CRUD 或其他业务逻辑方法。 
 * ### 接口详细说明： 
 * - `/space/tactic/system/attack_frame`: 
 * - 功能：返回 ATT&CK 框架的 JSON 数据。 
 * - 文件路径由 `JsonFileResponseEnum.ATTACK.getFilePath()` 提供。 
 * - `/space/tactic/system/engage_frame`: 
 * - 功能：返回 Engage 框架的 JSON 数据。 
 * - 文件路径由 `JsonFileResponseEnum.ENGAGE.getFilePath()` 提供。 
 * - `/space/tactic/system/defend_frame`: 
 * - 功能：返回 D3FEND 框架的 JSON 数据。 
 * - 文件路径由 `JsonFileResponseEnum.DEFEND.getFilePath()` 提供。 
 * ### 异常处理： 
 * - 如果文件读取失败，会抛出 `IOException` 并由调用方处理。 
 * ### 总结： 
 * 该控制器专注于提供三种防御技战法框架的数据服务，通过简单的文件读取操作实现数据返回。代码结构清晰，职责单一，符合 RESTful 设计规范，并支持 Swagger 文档生成。 
**/
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


    /**
     * 获取攻击框架
     *
     * @return 响应实体<string>
     * @throws @throws IOException io异常
     * @see ResponseEntity<String>
     */
    @ApiOperation("ATT&CK框架")
    @GetMapping("/attack_frame")
    public ResponseEntity<String> getAttackFrame() throws IOException {
        File json = ResourceUtils.getFile(JsonFileResponseEnum.ATTACK.getFilePath());
        String content = FileUtil.getContent(json);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(content);
    }

    /**
     * 获取Engage框架
     *
     * @return 响应实体<string>
     * @throws @throws IOException io异常
     * @see ResponseEntity<String>
     */
    @ApiOperation("Engage框架")
    @GetMapping("/engage_frame")
    public ResponseEntity<String> getEngageFrame() throws IOException {
        File json = ResourceUtils.getFile(JsonFileResponseEnum.ENGAGE.getFilePath());
        String content = FileUtil.getContent(json);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(content);
    }

    /**
     * 获取保卫框架
     *
     * @return 响应实体<string>
     * @throws @throws IOException io异常
     * @see ResponseEntity<String>
     */
    @ApiOperation("D3FEND框架")
    @GetMapping("/defend_frame")
    public ResponseEntity<String> getDefendFrame() throws IOException {
        File json = ResourceUtils.getFile(JsonFileResponseEnum.DEFEND.getFilePath());
        String content = FileUtil.getContent(json);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(content);
    }
}
