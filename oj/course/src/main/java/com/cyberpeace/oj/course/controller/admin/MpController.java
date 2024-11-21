package com.cyberpeace.oj.course.controller.admin;

import com.alibaba.fastjson.JSONObject;
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
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "转发mp接口api")
@RestController
@RequestMapping("/oj/mp")
@RequiresAuthentication
public class MpController {

    /**
     * 远程终端Service
     */
    @Autowired
    private RemoteTerminalService remoteTerminalService;

    /**
     * 远程拓扑service
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
	 * @see BaseRsp
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
	 * @return {@code BaseRsp }
	 * @throws
	 * @see BaseRsp
	 */
    @ApiOperation("获取镜像分类")
	@GetMapping("/devops/cluster/types/image-mg-categories")
    public BaseRsp getImageCategories() {
        return new BaseRsp(remoteTerminalService.getAllCategorie());
    }

	/**
	 * 查询dynamips相关的信息
	 *  /api/mp/devops/cluster/images/dynamic_image_info
	 *
	 * @return {@code BaseRsp }
	 * @throws
	 * @see BaseRsp
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
	 * @return {@code BaseRsp }
	 * @throws
	 * @see BaseRsp
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
	 * @return {@code BaseRsp }
	 * @throws
	 * @see BaseRsp
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
	 * @see BaseRsp
	 */
    @ApiOperation("场景模板列表")
	@GetMapping("/cscene/cscenes")
    public BaseRsp cscenes(@ApiParam TopoTemplatePageReq topoTemplatePageReq) {
        return new BaseRsp(remoteTopoService.getTopoTemplatePage(topoTemplatePageReq));
    }

	/**
	 * 查询所有靶标信息
	 * /api/mp/devops/cluster/target/listAll
	 *
	 * @param targetListReq 目标列表请求体
	 * @return {@code BaseRsp }
	 * @throws
	 * @see TargetListReq
	 * @see BaseRsp
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
	 * @see BaseRsp
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
	 * @see BaseRsp
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
	 * @see BaseRsp
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
	 * @see BaseRsp
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
	 * @see BaseRsp
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
	 * @see BaseRsp
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
	 * @see BaseRsp
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
	 * /devops/cluster/images/${id}/snapshot/list
	 *
	 * @param uuid uuid
	 * @return {@code BaseRsp }
	 * @throws
	 * @see String
	 * @see BaseRsp
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
	 * @see BaseRsp
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
	 * @see BaseRsp
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
	 * @see BaseRsp
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
	 * @see BaseRsp
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
	 * @see BaseRsp
	 */
	@ApiOperation(value = "保存（创建/更新）场景", notes = "请忽略test_scenes和app_scenes字段")
	@ApiImplicitParam(name = "csceneBO", value = "请求体", dataTypeClass = JSONObject.class)
	@PostMapping("/cscene/cscenes/sync")
	public BaseRsp sync(@RequestBody JSONObject csceneBO) {
		return new BaseRsp(remoteTopoService.sync(csceneBO));
	}

}
