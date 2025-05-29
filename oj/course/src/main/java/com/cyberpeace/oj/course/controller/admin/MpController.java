package com.cyberpeace.oj.course.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.bo.ClusterImagePageReq;
import com.cyberpeace.integratation.service.bo.TargetListReq;
import com.cyberpeace.integratation.service.bo.TopoTemplatePageReq;
import com.cyberpeace.integratation.service.bo.mp.MibInfoQueryRequest;
import com.cyberpeace.integratation.service.bo.mp.RealEquipmentPageRequest;
import com.cyberpeace.integratation.service.bo.mp.VirtualUsbQueryParams;
import com.cyberpeace.oj.practice.controller.req.LoadResourceReq;

import io.swagger.annotations.*;

/**
 * 中台Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/12/04
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `MpController` 的 Spring MVC 控制器类，主要用于处理与中台（MP）相关的 HTTP 请求。以下是对该代码的简要分析： 
 * ### 类定义和注解 
 * - **包声明**：`com.cyberpeace.oj.course.controller.admin` 
 * - **类名**：`MpController` 
 * - **注解**： 
 * - `@RestController`：标识这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping("/oj/mp")`：定义了所有方法的公共路径前缀 `/oj/mp`。 
 * - `@RequiresAuthentication`：表示需要用户认证才能访问这些 API。 
 * ### 成员变量 
 * - `remoteTerminalService` 和 `remoteTopoService`：这两个服务通过 `@Autowired` 注入，分别提供远程终端和远程拓扑的相关功能。 
 * ### 方法 
 * 每个方法都对应一个特定的 HTTP 请求，并且返回 `BaseRsp` 对象。以下是一些主要方法的简要描述： 
 * 1. **获取镜像列表** 
 * - `@GetMapping("/devops/cluster/images/page")` 
 * - 处理 `/oj/mp/devops/cluster/images/page` GET 请求。 
 * - 参数：`ClusterImagePageReq req` 
 * 2. **获取镜像分类** 
 * - `@GetMapping("/devops/cluster/types/image-mg-categories")` 
 * - 处理 `/oj/mp/devops/cluster/types/image-mg-categories` GET 请求。 
 * 3. **删除场景模板** 
 * - `@DeleteMapping("/cscene/cscenes")` 
 * - 处理 `/oj/mp/cscene/cscenes` DELETE 请求。 
 * - 参数：`String id` 
 * 4. **上传场景中的附件** 
 * - `@PostMapping("/cscene/file/attachment_upload")` 
 * - 处理 `/oj/mp/cscene/file/attachment_upload` POST 请求。 
 * - 参数：`MultipartFile file` 
 * 5. **新增场景模板** 
 * - `@PostMapping("/cscene/cscenes/sync")` 
 * - 处理 `/oj/mp/cscene/cscenes/sync` POST 请求。 
 * - 参数：`JSONObject csceneBO` 
 * 6. **其他方法**：包括获取镜像分类、查询靶标信息、上传文件等，每种方法都有相应的注解来定义其行为和路径。 
 * ### 总结 
 * `MpController` 是一个处理与中台相关的 HTTP 请求的控制器类。它提供了多种操作，如获取镜像列表、删除场景模板、上传文件等。每个方法都通过注解定义了其对应的 HTTP 请求类型和路径，并返回 `BaseRsp` 对象。这些方法通过依赖注入的服务对象来实现具体的功能。 
**/
@Api(tags = "转发mp接口api")
@RestController
@RequestMapping("/oj/mp")
@RequiresAuthentication
public class MpController {

	/**
	 * 远程终端Service
	 *
	 * @see RemoteTerminalService
	 */
	@Autowired
    private RemoteTerminalService remoteTerminalService;

	/**
	 * 远程拓扑service
	 *
	 * @see RemoteTopoService
	 */
	@Autowired
    private RemoteTopoService remoteTopoService;

	/**
	 * 获取镜像列表
	 * /api/mp/devops/cluster/images/page
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp }
	 * @throws
	 * @see ClusterImagePageReq
	 * @see ClusterImagePageReq
	 */
	@ApiOperation("获取镜像列表")
	@GetMapping("/devops/cluster/images/page")
    public BaseRsp getImagePage(@ApiParam ClusterImagePageReq req){
        JSONObject res = remoteTerminalService.pageImages(req);
        return new BaseRsp(res);
    }

	/**
	 * 获取镜像分类
	 * /api/mp/devops/cluster/types/image-mg-categories
	 *
	 * @throws
	 * @see BaseRsp@return {@code BaseRsp }
	 */
	@ApiOperation("获取镜像分类")
	@GetMapping("/devops/cluster/types/image-mg-categories")
    public BaseRsp getImageCategories() {
        return new BaseRsp(remoteTerminalService.getAllCategorie());
    }

	/**
	 * 查询dynamips相关的信息
	 * /api/mp/devops/cluster/images/dynamic_image_info
	 *
	 * @throws
	 * @see BaseRsp@return {@code BaseRsp }
	 */
	@ApiOperation("查询dynamips相关的信息")
	@GetMapping("/devops/cluster/images/dynamic_image_info")
    public BaseRsp getDynamicImageInfo() {
        return new BaseRsp(remoteTerminalService.getDynamicImageInfo());
    }

	/**
	 * 查询靶标对应的探针系统分类
	 * /api/mp/cscene/config/system_type_to_agent_support
	 *
	 * @throws
	 * @see BaseRsp@return {@code BaseRsp }
	 */
	@ApiOperation("查询靶标对应的探针系统分类")
	@GetMapping("/cscene/config/system_type_to_agent_support")
    public BaseRsp systemTypeToAgentSupport() {
        return new BaseRsp(remoteTerminalService.getAgentSupport());
    }

	/**
	 * 查询常用靶标信息
	 * /api/mp/devops/cluster/target/common-targets
	 *
	 * @throws
	 * @see BaseRsp@return {@code BaseRsp }
	 */
	@ApiOperation("查询常用靶标信息")
	@GetMapping("/devops/cluster/target/common-targets")
    public BaseRsp commonTargets() {
        return new BaseRsp(remoteTerminalService.getCommonTargets());
    }

	/**
	 * 场景模板列表
	 * /api/mp/cscene/cscenes
	 *
	 * @param topoTemplatePageReq 拓扑模板分页请求体
	 * @return {@code BaseRsp }
	 * @throws
	 * @see TopoTemplatePageReq
	 * @see TopoTemplatePageReq
	 */
	@ApiOperation("场景模板列表")
	@GetMapping("/cscene/cscenes")
    public BaseRsp cscenes(@ApiParam TopoTemplatePageReq topoTemplatePageReq) {
        return new BaseRsp(remoteTopoService.getTopoTemplatePage(topoTemplatePageReq));
    }

	/**
	 * 删除场景模板
	 * /cscene/cscenes?id=xx
	 *
	 * @param id id
	 * @return {@code BaseRsp }
	 * @throws
	 * @see String
	 * @see String
	 */
	@ApiOperation("删除场景模板")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "场景模板id", dataTypeClass = String.class) })
	@DeleteMapping("/cscene/cscenes")
	public BaseRsp delete(@RequestParam(required = true) String id) {
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		return new BaseRsp(remoteTopoService.deleteById(id));
	}

	/**
	 * 查询所有靶标信息
	 * /api/mp/devops/cluster/target/listAll
	 *
	 * @param targetListReq 目标列表请求体
	 * @return {@code BaseRsp }
	 * @throws
	 * @see TargetListReq
	 * @see TargetListReq
	 */
	@ApiOperation("查询所有靶标信息")
	@GetMapping("/devops/cluster/target/listAll")
    public BaseRsp targetListAll(@ApiParam TargetListReq targetListReq) {
        return new BaseRsp(remoteTerminalService.targetList(targetListReq));
    }

	/**
	 * 查询所有二级分类
	 * /api/mp/devops/cluster/types/l2-categories?firstTypeId=
	 *
	 * @param firstTypeId 第一类型id
	 * @return {@code BaseRsp }
	 * @throws
	 * @see Integer
	 * @see Integer
	 */
	@ApiOperation("查询所有二级分类")
	@GetMapping("/devops/cluster/types/l2-categories")
    public BaseRsp l2Ccategories(@RequestParam(value = "firstTypeId", required = false) Integer firstTypeId) {
        return new BaseRsp(remoteTerminalService.getL2categories(firstTypeId));
    }

	/**
	 * 根据镜像名称或快照名称查询镜像名称和快照历史信息
	 * /api/mp/devops/cluster/images/snapshot/by-name?name=
	 *
	 * @param name 名称
	 * @return {@code BaseRsp }
	 * @throws
	 * @see String
	 * @see String
	 */
	@ApiOperation("根据镜像名称或快照名称查询镜像名称和快照历史信息")
	@GetMapping("/devops/cluster/images/snapshot/by-name")
    public BaseRsp snapshotByName(@ApiParam String name) {
        return new BaseRsp(remoteTerminalService.snapshotByName(name));
    }

	/**
	 * 上传场景中的附件
	 * /api/mp/cscene/file/attachment_upload
	 *
	 * @param file
	 * @return {@code BaseRsp }
	 * @throws
	 * @see LoadResourceReq
	 * @see LoadResourceReq
	 */
	@ApiOperation("上传场景中的附件")
	@PostMapping("/cscene/file/attachment_upload")
	@ApiImplicitParam(name = "file", value = "场景中的附件", dataTypeClass = MultipartFile.class)
	public BaseRsp uploadAttachment(@RequestParam(value = "file") MultipartFile file) {
		return new BaseRsp(remoteTopoService.uploadSceneAttachment(file));
	}

	/**
	 * 上传图标
	 * /cscene/file/icon_upload
	 *
	 * @param file
	 * @return {@code BaseRsp }
	 * @throws
	 * @see LoadResourceReq
	 * @see LoadResourceReq
	 */
	@ApiOperation("上传场景中的图标")
	@PostMapping("/cscene/file/icon_upload")
	@ApiImplicitParam(name = "file", value = "场景中的图标", dataTypeClass = MultipartFile.class)
	public BaseRsp uploadIcon(@RequestParam(value = "file") MultipartFile file) {
		return new BaseRsp(remoteTopoService.uploadIcon(file));
	}

	/**
	 * 上传场景中的模板截图
	 *
	 * @param file 文件
	 * @return {@code BaseRsp }
	 * @throws
	 * @see MultipartFile
	 * @see MultipartFile
	 */
	@ApiOperation("上传场景中的模板截图")
	@ApiImplicitParam(name = "file", value = "场景模板截图", dataTypeClass = MultipartFile.class)
	@PostMapping("/cscene/file/scene_template_screenshot_upload")
	public BaseRsp sceneTemplateScreenshotUpload(@RequestParam(value = "file") MultipartFile file) {
		return new BaseRsp(remoteTopoService.uploadSceneTemplateSnapshot(file));
	}

	/**
	 * 上传节点秘钥
	 * /cscene/file/private_key_upload
	 *
	 * @param file 文件
	 * @return {@code BaseRsp }
	 * @throws
	 * @see MultipartFile
	 * @see MultipartFile
	 */
	@ApiOperation("上传节点秘钥")
	@ApiImplicitParam(name = "file", value = "节点秘钥", dataTypeClass = MultipartFile.class)
	@PostMapping("/cscene/file/private_key_upload")
	public BaseRsp privateKeyUpload(@RequestParam(value = "file") MultipartFile file) {
		return new BaseRsp(remoteTopoService.uploadPrivateKey(file));
	}

	/**
	 * 轮询场景链路状态
	 * /cscene/cscene_instances/link_status/
	 *
	 * @param id id
	 * @return {@code BaseRsp }
	 * @throws
	 * @see String
	 * @see String
	 */
	@ApiOperation("场景链路状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "场景实例id", dataTypeClass = String.class) })
	@GetMapping("/cscene/cscene_instances/link_status")
	public BaseRsp linkStatus(@RequestParam(value = "id") String id) {
		return new BaseRsp(remoteTerminalService.getLinkStatus(id));
	}

	/**
	 * 查询快照历史，用于获取历史版本的个数
	 * /devops/cluster/images/$
	 * {
	 * id
	 * }
	 * /snapshot/list
	 *
	 * @param uuid uuid
	 * @return {@code BaseRsp }
	 * @throws
	 * @see String
	 * @see String
	 */
	@ApiOperation("查询快照历史")
	@ApiImplicitParam(name = "uuid", value = "靶标id", paramType = "path", dataTypeClass = String.class)
	@GetMapping("/devops/cluster/images/{uuid}/snapshot/list")
	public BaseRsp listSnapshot(@PathVariable("uuid") String uuid){
		return new BaseRsp(remoteTerminalService.listSnapshot(uuid));
	}

	/**
	 * 查询实体设备信息，鼠标悬浮数据信息框
	 * /devops/cluster/real-equipment/page
	 *
	 * @param request 请求
	 * @return {@code BaseRsp }
	 * @throws
	 * @see RealEquipmentPageRequest
	 * @see RealEquipmentPageRequest
	 */
	@ApiOperation("分页查询")
	@GetMapping("/devops/cluster/real-equipment/page")
	public BaseRsp pageReal(RealEquipmentPageRequest request){
		return new BaseRsp(remoteTerminalService.pageRealEquipment(request));
	}

	/**
	 * 查询虚拟usb列表
	 * /devops/cluster/virtual_usb/page
	 *
	 * @param queryParams 查询参数
	 * @return {@code BaseRsp }
	 * @throws
	 * @see VirtualUsbQueryParams
	 * @see VirtualUsbQueryParams
	 */
	@ApiOperation("分页查询虚拟USB信息")
	@GetMapping("/devops/cluster/virtual_usb/page")
	public BaseRsp pageUsb(VirtualUsbQueryParams queryParams) {
		return new BaseRsp(remoteTerminalService.pageUsb(queryParams));
	}

	/**
	 * SNMP采集 上传mib文件
	 * /cscene/file/mib_import
	 *
	 * @param file 文件
	 * @return {@code BaseRsp }
	 * @throws
	 * @see MultipartFile
	 * @see MultipartFile
	 */
	@ApiOperation("上传mib文件")
	@ApiImplicitParam(name = "file", value = "mib文件", dataTypeClass = MultipartFile.class)
	@PostMapping("/cscene/file/mib_import")
	public BaseRsp sceneMibFileUpload(@RequestParam(value = "file") MultipartFile file){
		return new BaseRsp(remoteTopoService.uploadMib(file));
	}

	/**
	 * 获取mib节点信息
	 * /cscene/protocol/collection/mib_nodeinfo
	 *
	 * @param queryRequest 查询请求
	 * @return {@code BaseRsp }
	 * @throws
	 * @see MibInfoQueryRequest
	 * @see MibInfoQueryRequest
	 */
	@ApiOperation(value = "mib节点信息查询")
	@GetMapping("/cscene/protocol/collection/mib_nodeinfo")
	public BaseRsp queryMibNodeInfo(@Validated MibInfoQueryRequest queryRequest) {
		return new BaseRsp(remoteTopoService.getMibNodeInfo(queryRequest));
	}

	/**
	 * 新增场景模板
	 * /cscene/cscenes/sync
	 *
	 * @param csceneBO CSenseBo
	 * @return {@code BaseRsp }
	 * @throws
	 * @see JSONObject
	 * @see JSONObject
	 */
	@ApiOperation(value = "保存（创建/更新）场景", notes = "请忽略test_scenes和app_scenes字段")
	@ApiImplicitParam(name = "csceneBO", value = "请求体", dataTypeClass = JSONObject.class)
	@PostMapping("/cscene/cscenes/sync")
	public BaseRsp sync(@RequestBody JSONObject csceneBO) {
		return new BaseRsp(remoteTopoService.sync(csceneBO));
	}

}

