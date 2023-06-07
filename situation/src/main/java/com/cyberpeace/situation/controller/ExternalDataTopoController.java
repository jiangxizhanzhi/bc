package com.cyberpeace.situation.controller;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.situation.*;
import com.cyberpeace.integratation.model.situation.req.NodeActionReq;
import com.cyberpeace.integratation.model.situation.req.NodeIdApiReq;
import com.cyberpeace.integratation.model.situation.req.NodeIdPageReq;
import com.cyberpeace.integratation.model.situation.req.TopoReq;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.topo.DemoTopo;
import com.cyberpeace.situation.controller.req.NodeActionPageReq;
import com.cyberpeace.situation.controller.req.NodePageReq;
import com.cyberpeace.situation.dao.entity.TopoConfig;
import com.cyberpeace.situation.dao.mapper.TopoConfigMapper;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 外部数据-拓扑分析
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Api(tags = "外部数据-拓扑分析")
@RestController
@RequiresAuthentication
@RequestMapping("/third/")
public class ExternalDataTopoController {

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private TopoConfigMapper topoConfigMapper;

	@Autowired
	private SituationHandlerService situationHandlerService;

	private volatile Boolean demo = true;

	/**
	 * 获取节点链路状态
	 *
	 * @param nodeIdApiReq 节点ID请求
	 * @return {@code BaseRsp<NodeConnectionContext>}
	 */
	@ApiOperation("态势-获取节点链路状态")
	@PostMapping("/node/connection/")
	public BaseRsp<NodeConnectionContext>
			getNodeConnection(@RequestBody @Validated NodeIdApiReq nodeIdApiReq) {

		return new BaseRsp<>(remoteSituationService.getNodeConnection(nodeIdApiReq));
	}

	/**
	 * 获取节点基础信息
	 *
	 * @param nodeIdApiReq 节点ID请求
	 * @return {@code BaseRsp<NodeInfoContext>}
	 */
	@ApiOperation("态势-获取节点基础信息")
	@PostMapping("/node/info/")
	public BaseRsp<NodeInfoContext> getNodeInfo(@RequestBody @Validated NodeIdApiReq nodeIdApiReq) {
		return new BaseRsp<>(remoteSituationService.getNodeInfo(nodeIdApiReq));
	}

	/**
	 * 获取相似节点
	 *
	 * @param nodeIdApiReq 节点ID请求
	 * @return {@code BaseRsp<List<NodeBasicInfoContext>>}
	 */
	@ApiOperation("态势-获取相似节点")
	@PostMapping("/node/similar_nodes/")
	public BaseRsp<List<NodeBasicInfoContext>>
			getSimilarNodeList(@RequestBody @Validated NodeIdApiReq nodeIdApiReq) {
		List<NodeBasicInfoContext> rsp = remoteSituationService.getSimilarNodes(nodeIdApiReq);
		return new BaseRsp<>(rsp);
	}

	/**
	 * 获取节点行为数据
	 *
	 * @param nodeActionPageReq 节点行为分页请求
	 * @return {@code BaseRsp<ListRsp<NodeActionContext>>}
	 */
	@ApiOperation("态势-获取节点行为数据")
	@GetMapping("/node/action/")
	public BaseRsp<ListRsp<NodeActionContext>> getNodeAction(NodeActionPageReq nodeActionPageReq) {
		return new BaseRsp<>(remoteSituationService
				.getNodeAction(BeanUtils.copy(nodeActionPageReq, NodeActionReq.class)));
	}

	/**
	 * 获取节点行为趋势
	 *
	 * @param nodeIdApiReq 节点ID请求
	 * @return {@code BaseRsp<List<NodeTrendContext>>}
	 */
	@ApiOperation("态势-获取节点行为趋势")
	@PostMapping("/node/trend/")
	public BaseRsp<List<NodeTrendContext>>
			getNodeTrend(@RequestBody @Validated NodeIdApiReq nodeIdApiReq) {

		return new BaseRsp<>(remoteSituationService.getNodeTrend(nodeIdApiReq));
	}

	/**
	 * 获取用户行为数据
	 *
	 * @param nodeListApiReq 节点列表请求
	 * @return {@code BaseRsp<ListRsp<UserActionContext>>}
	 */
	@ApiOperation("态势-获取用户行为数据")
	@GetMapping("/user/action/")
	public BaseRsp<ListRsp<UserActionContext>> getUserAction(NodePageReq nodeListApiReq) {

		return new BaseRsp<>(remoteSituationService
				.getUserAction(BeanUtils.copy(nodeListApiReq, NodeIdPageReq.class)));
	}

	/**
	 * 获取用户行为趋势
	 *
	 * @param nodeIdApiReq 节点ID请求
	 * @return {@code BaseRsp<List<UserTrendContext>>}
	 */
	@ApiOperation("态势-获取用户行为趋势")
	@PostMapping("/user/trend/")
	public BaseRsp<List<UserTrendContext>>
			getUserTrend(@RequestBody @Validated NodeIdApiReq nodeIdApiReq) {

		return new BaseRsp<>(remoteSituationService.getUserTrend(nodeIdApiReq));
	}

	/**
	 * 获取节点脆弱性分析信息
	 *
	 * @param nodeIdApiReq 节点ID请求
	 * @return {@code BaseRsp<NodeWeakInfoContext>}
	 */
	@ApiOperation("态势-获取节点脆弱性分析信息")
	@PostMapping("/node/weak/info/")
	public BaseRsp<NodeWeakInfoContext>
			getNodeWeakInfo(@RequestBody @Validated NodeIdApiReq nodeIdApiReq) {
		return new BaseRsp<>(remoteSituationService.getNodeWeakInfo(nodeIdApiReq));
	}

	/**
	 * 获取节点脆弱性列表
	 *
	 * @param nodePageReq 节点分页请求
	 * @return {@code BaseRsp<ListRsp<NodeWeakContext>>}
	 */
	@ApiOperation("态势-获取节点脆弱性列表")
	@GetMapping("/node/weak/list/")
	public BaseRsp<ListRsp<NodeWeakContext>> getNodeWeakList(NodePageReq nodePageReq) {

		return new BaseRsp<>(remoteSituationService
				.getNodeWeakList(BeanUtils.copy(nodePageReq, NodeIdPageReq.class)));
	}

	/**
	 * 获取拓扑数据
	 *
	 * @param topoReq 拓扑请求
	 * @return {@code BaseRsp<TopoContext>}
	 */
	@ApiOperation("想定-获取拓扑数据")
	@PostMapping("/topo/")
	public BaseRsp<TopoContext> getTopo(@RequestBody @Validated TopoReq topoReq) {

		// Demo模式下，返回样例拓扑
		if (DemoTopo.DEFAULT.getCode().equals(topoReq.getEnvId()) && demo) {
			return new BaseRsp<>(JsonUtil.toJsonObject(
					JsonUtil.toJsonString(getJsonByName("demoTopo")), TopoContext.class));
		}

		// Demo模式下，返回5000节点的测试拓扑
		if (DemoTopo.TEST.getCode().equals(topoReq.getEnvId()) && demo) {
			return new BaseRsp<>(JsonUtil.toJsonObject(
					JsonUtil.toJsonString(getJsonByName("demo5000")), TopoContext.class));
		}

		TopoContext topoContext = remoteSituationService.getTopo(topoReq, false);

		// 处理编排后的拓扑坐标
		if (StringUtils.isNotBlank(topoReq.getEnvId())) {
			TopoConfig topoConfig = topoConfigMapper.selectByEnvId(topoReq.getEnvId());
			if (topoConfig != null) {
				JSONObject json = JsonUtil.toJsonObject(topoConfig.getConfig(), JSONObject.class);
				Map<String, ElementSlimContext> elementSlimContextMap = JSONObject.parseObject(
						json.toJSONString(),
						new TypeReference<Map<String, ElementSlimContext>>() {});
				topoContext.getElements().forEach((key, element) -> {
					element.setPosition(elementSlimContextMap
							.getOrDefault(key, new ElementSlimContext()).getPosition());
				});
			}
		}

		return new BaseRsp<>(topoContext);
	}

	/**
	 * 通过文件名获取json
	 *
	 * @param name 文件名
	 * @return {@code JSONObject}
	 */
	private JSONObject getJsonByName(String name) {
		InputStream inputStream = getClass().getResourceAsStream("/" + name + ".json");
		return JsonUtil.toJsonObject(inputStream, JSONObject.class).getJSONObject("data");
	}

	/**
	 * 切换拓扑模式
	 *
	 * @param mode 拓扑模式
	 * @return {@code BaseRsp<Boolean>}
	 */
	@ApiOperation("系统-切换拓扑模式")
	@GetMapping("/topo/mode/")
	public BaseRsp<Boolean> changeTopoMode(@ApiParam(name = "mode", value = "拓扑模式",
			required = false) @RequestParam(value = "mode", required = false) Boolean mode) {
		demo = mode;
		return new BaseRsp<>(mode);
	}

}
