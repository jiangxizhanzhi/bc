package com.cyberpeace.cr.evaluation.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSceneMapRsp;
import com.cyberpeace.cr.evaluation.dao.mode.*;
import com.cyberpeace.cr.evaluation.dao.mode.probe.NetworkProbeData;
import com.cyberpeace.cr.evaluation.dao.mode.probe.data.ReportData;
import com.cyberpeace.cr.evaluation.dao.mode.topo.*;
import com.cyberpeace.integratation.model.mp.*;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Api(tags = "靶场测评-测绘")
@RestController
@Slf4j
@RequestMapping("/cr/eval/template/scene")
public class EvalSceneMapController {
	@Autowired
	private RemoteSceneService remoteSceneService;

	private HashMap<Integer, Integer> roleMap = new HashMap<Integer, Integer>() {{
		put(3, 0); // 外部网络
		put(22, 1); // 交换机
		put(21, 2); // 路由器
		put(23, 3); // 防火墙
		put(4, 4);  // 终端
		put(7, 4); // 仿真节点
		put(1, 1);
	}};

	private HashMap<Integer, Integer> roleReflexMap = new HashMap<Integer, Integer>() {{
		put(0, 3); // 外部网络
		put(1, 22); // 交换机
		put(2, 21); // 路由器
		put(3, 23); // 防火墙
		put(4, 4);  // 终端
		put(4, 7); // 仿真节点
		put(1, 1);
	}};

	@ApiOperation("导入测绘拓扑")
//	@RequiresPermissions(value = { MenuConstant.CMS_CR_EVALUATION_TEMPLATE,MenuConstant. WEB_CR_EVALUATION }, logical = Logical.OR)
	@PostMapping("/map")
	public BaseRsp<EvalSceneMapRsp> loadMap(
			@RequestParam("file") MultipartFile file
	) throws IOException {
		// 1、解析导入文件 获取节点、节点端口以及节点间连线信息
		Topology topo = analyzeFiles(file);

		// 2、从中台获取实现导入的文件节点包含的镜像信息
		List<ImagesByIdVO> imageList = remoteSceneService.getImageList();
		Map<String, String> imagesMap = StreamApiUtils.toMap(imageList, ImagesByIdVO::getName, s -> s.getUuid());

		// 执行转换逻辑
		// 节点镜像对应关系在elementMap中 取出上述获取的镜像信息中仅包含文件涉及的镜像
		Map<String, Element> elementMap = topo.getElementMap();
		List<String> images = elementMap.values().stream().filter(element -> element.getImage() != null).
				map(element -> imagesMap.get(element.getImage())).distinct().collect(Collectors.toList());
		List<String> ids = new ArrayList<>();
		for (String uuid : images) {
			if (uuid != null && StringUtils.isNotEmpty(uuid)) {
				ids.add(uuid);
			}
		}
		if(ids.isEmpty()){
			throw new BusinessException(ErrorCode.INVALID_IMAGES);
		}
		List<ImagesByIdVO> imageListByuuids = remoteSceneService.getImageListByuuids(ids);
		Map<String, ImagesByIdVO> imagesByIdVOMap = StreamApiUtils.toMap(imageListByuuids, ImagesByIdVO::getName, s -> s);

		// 3、遍历解析出的节点列表 将节点对应的镜像信息填充后返回前端
		List<Element> elements = topo.getElements();
		elements = elements.stream().filter(e-> null !=e.getImage()).collect(Collectors.toList());
		List<Port> ports = topo.getPorts();
		Map<String, List<Port>> portMap = StreamApiUtils.groupBy(ports, Port::getElement);
		List<TopoElement> topoElements = new ArrayList<>();
		List<Connections> sceneConnections = new ArrayList<>();
		JSONObject cpElements = new JSONObject();
		JSONObject elementPorts = new JSONObject();
		HashMap<String, String> map = new HashMap<>();
		HashMap<String, ImagesByIdVO> elementPortMap = new HashMap<>();

		for(Element element:elements){
			TopoElement topoElement = new TopoElement();
			String id = IDGeneratorUtil.getID();
			//TODO 设备名称
			String name = element.getName() ;
			Element element1 = elementMap.get(element.getId());
			String image = element1.getImage();
			if(null == image){
				continue;
				// TODO 先测试 未匹配到镜像信息 则抛异常
//				throw new BusinessException(ErrorCode.INVALID_ELEMENT);
			}
			ImagesByIdVO imagesPageVO = imagesByIdVOMap.get(image);
			if (imagesPageVO == null) {
				continue;
			}
			// 中台获取的镜像角色
//			imagesPageVO.setRole(roleMap.get(imagesPageVO.getRole()));
			elementPortMap.put(id, imagesPageVO);
			getTopoElement(topoElement, id, name, imagesPageVO, element);
			CpcloudElement elementObj = getCpcloudElement(imagesPageVO);
			cpElements.put(id, elementObj);


			List<Port> portList = portMap.get(element.getId());
			if(portList.isEmpty()){
				portList = portMap.get(element.getId() + "/24");
			}
			JSONArray objects = new JSONArray();
			int portIndex = 1;
			// 端口id 仿真节点 1个端口 4- image_type vm 虚拟机 container 容器 连线超出当前max_port_num  需要手动生成 自动补足
			// 网关 ext_gateway -port——configs 几条数据则 几个端口 判断连线是否超出当前端口数 超出则自动补足 区分wan lan口 port_type 2-lan 除了2都是wan口
			// 3.1、根据端口信息以及 设备关联的镜像信息中的接入配置信息 填充设备的默认端口信息
			initElement(elementPorts, map, elementPortMap, id, portList, objects, portIndex);
			topoElement.setPorts(objects);
			topoElements.add(topoElement);

		}

		List<Connection> connections = topo.getConnections();
		Map<String, TopoElement> topoElementMap = StreamApiUtils.toMap(topoElements, TopoElement::getName, s -> s);
		Map<String, Integer> portsMap = new HashMap<>();
		// 4、遍历连线列表 根据该信息填充connection返回给前端 每一根连线包含 source 、target(端口id 由port_设备id_电口/光口_端口数) 默认用电口 连线id 并根据设备的连线数 设置相同数量的端口数
		setConnection(topoElements, sceneConnections, elementPorts, map, connections, topoElementMap, portsMap);

		// 5、根据设备的连线数 排列出节点重要度 并结合布局算法 计算出节点的坐标
		setElementPosition(topoElements, sceneConnections);

		// 6、根据设备的端口数 同步设备的网卡数 以及电口数
		for(TopoElement topoElement:topoElements){
			String ip = topoElement.getName();
			if(portsMap.containsKey(ip)){
				int index = topoElements.indexOf(topoElement);
				Integer integer = portsMap.get(ip);
				topoElement.setMaxPortNumber(integer);
				topoElements.set(index, topoElement);
			}
			CpcloudElement jsonObject = cpElements.getJSONObject(topoElement.getId()).toJavaObject(CpcloudElement.class);
			jsonObject.setElectricPortNumber(topoElement.getPorts().isEmpty() ? 0 : topoElement.getPorts().size());
			cpElements.put(topoElement.getId(), jsonObject);
		}
		EvalSceneMapRsp rsp = new EvalSceneMapRsp();
		SceneTopology sceneTopology = new SceneTopology();
		sceneTopology.setElements(topoElements);
		sceneTopology.setConnections(sceneConnections);
		rsp.setSceneTopology(sceneTopology);
		Plugins plugins = new Plugins();
		Cpcloud cpcloud = new Cpcloud();
		cpcloud.setElement_ports(elementPorts);
		cpcloud.setElements(cpElements);
		plugins.setCpcloud(cpcloud);
		rsp.setPlugins(plugins);
		return new BaseRsp<>(rsp);
	}

	private void initElement(JSONObject elementPorts, HashMap<String, String> map, HashMap<String, ImagesByIdVO> elementPortMap, String id, List<Port> portList, JSONArray objects, int portIndex) {
		for(Port port: portList){
			String value = "port_" + id + "_" + portIndex;
			String portId = port.getId();
			JSONObject item = new JSONObject();
			ImagesByIdVO imagesByIdVO = elementPortMap.get(id);
			ExtGateway gateway = imagesByIdVO.getExtGateway();
			ExtVm extVm = imagesByIdVO.getExtVm();
			ExtDocker extDocker = imagesByIdVO.getExtDocker();
			JSONObject object = new JSONObject();
			if(null != gateway){
				item.put("access_modes", gateway);
				object.put("access_modes", gateway);


			} else if(extVm != null && null != extVm.getAccessProtocols()){
				item.put("access_modes", extVm.getAccessProtocols());
				object.put("access_modes", extVm.getAccessProtocols());

			} else if(extDocker != null && null != extDocker.getAccessProtocols()){
				item.put("access_modes", extDocker.getAccessProtocols());
				object.put("access_modes", extDocker.getAccessProtocols());

			}
			if (imagesByIdVO != null && imagesByIdVO.getRole() != null) {
				item.put("connect_mode", (imagesByIdVO.getRole() == 1 || imagesByIdVO.getRole() == 2) && !"simple".equals(imagesByIdVO.getImageType()) ? "simple":null);
			}
			item.put("firewall_rules", null);
			item.put("logic_network_id", "");
			item.put("resource_id", "");
			elementPorts.put(value, item);
			map.put(portId, value);
			object.put("id", value);
			object.put("name", "eth0/" + (objects.isEmpty() ? 1 : objects.size() + 1));
			object.put("mac_addr", "");
			object.put("ip", null);
			objects.add(object);
			portIndex++;
		}
	}

	private void setConnection(List<TopoElement> topoElements, List<Connections> sceneConnections, JSONObject elementPorts, HashMap<String, String> map, List<Connection> connections, Map<String, TopoElement> topoElementMap, Map<String, Integer> portsMap) {
		for(Connection conn: connections){
			String from = conn.getFrom();
			String to = conn.getTo();
			// 连线两端找不到对应端口 则跳过不展示该连线
			if(!map.containsKey(from) || !map.containsKey(to)){
				continue;
			}

			portsMap.put(from, portsMap.containsKey(from) ? portsMap.get(from) + 1  : 1);
			portsMap.put(to, portsMap.containsKey(to) ? portsMap.get(to) + 1  : 1);


			TopoElement fromElement = topoElementMap.get(from);
			int fromIndex = topoElements.indexOf(fromElement);
			TopoElement toElement = topoElementMap.get(to);
			int toIndex = topoElements.indexOf(toElement);
			Connections connScene = new Connections();
			String id = IDGeneratorUtil.getID();
			connScene.setId(id);
			String fromPort = map.get(from);
			JSONObject fromJson = elementPorts.getJSONObject(fromPort);

			String toPort = map.get(to);
			JSONObject toJson = elementPorts.getJSONObject(toPort);

			String target = "port_" + fromElement.getId() + "_1";
			String source = "port_" + toElement.getId() + "_1";
			if(portsMap.get(from) != 1){
				target = updatePorts(elementPorts, topoElements, fromElement, fromIndex, fromJson, portsMap);
			}

			if(portsMap.get(to) != 1){
				source = updatePorts(elementPorts, topoElements, toElement, toIndex, toJson, portsMap);
			}


			connScene.setSource(source);
			connScene.setTarget(target);
			sceneConnections.add(connScene);
		}
	}

	private String updatePorts(JSONObject elementPorts, List<TopoElement> topoElements, TopoElement toElement, int toIndex, JSONObject fromJson, Map<String, Integer> portsMap) {
		String source;
		JSONObject toObject = new JSONObject();
		JSONArray toPorts = toElement.getPorts();
		Integer portNum = portsMap.get(toElement.getName());
		source = "port_" + toElement.getId() + "_" + portNum;
		toObject.put("id", source);
		toObject.put("name", "eth0/" + portNum);
		toObject.put("mac_addr", "");
		toObject.put("ip", null);
		toObject.put("access_modes", null);
		if(!toPorts.isEmpty()){
			JSONObject jsonObject = toPorts.getJSONObject(0);
			toObject.put("access_modes", jsonObject.get("access_modes"));
		}
		toPorts.add(toObject);
		toElement.setPorts(toPorts);
		topoElements.set(toIndex, toElement);
		elementPorts.put(source, fromJson);
		return source;
	}

	private void setElementPosition(List<TopoElement> topoElements, List<Connections> sceneConnections) {
		List<Node> nodes = new ArrayList<>();
		topoElements.forEach(a->{
			JSONArray aPorts = a.getPorts();
			Node node = new Node(a.getId());
			node.setDegree(aPorts.size());
			nodes.add(node);
		});
		Map<String, Node> nodeMap = StreamApiUtils.toMap(nodes, Node::getId, s -> s);
		List<Edge> edges = new ArrayList<>();
		sceneConnections.forEach(s->{
			String regex = "port_([a-fA-F0-9]+)_\\w+";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(s.getSource());
			Matcher matcher2 = pattern.matcher(s.getTarget());
			Node source1 = null;
			Node target1 = null;
			if (matcher.find()) {
				// 获取捕获组内容（即我们想要的部分）
				String extracted = matcher.group(1);
				source1 = nodeMap.get(extracted);
			} else {
				log.error("No match found.");
			}

			if (matcher2.find()) {
				// 获取捕获组内容（即我们想要的部分）
				String extracted = matcher2.group(1);
				target1 = nodeMap.get(extracted);
			} else {
				log.error("No match found.");
			}
			Edge edge = new Edge(source1, target1);
			edges.add(edge);
		});


		// 创建图对象
		Graph<Node, Edge> graph = new SparseGraph<>();

		// 添加节点和边到图中
		for (Node node : nodes) {
			graph.addVertex(node);
		}
		for (Edge edge : edges) {
			graph.addEdge(edge, edge.getSource(), edge.getTarget());
		}

		// 使用Fruchterman-Reingold布局算法进行布局
		Layout<Node, Edge> layout = new FRLayout<>(graph);
		layout.setSize(new Dimension(1920, 1080)); // 设置布局大小

		// 运行布局算法
		layout.initialize();
		layout.reset();

		// 获取节点的布局位置
		Map<Node, Point2D> nodePositions = new HashMap<>();
		for (Node node : nodes) {
			Point2D point = layout.apply(node);
			nodePositions.put(node, point);
		}

		// 打印节点位置
		for (Node node : nodes) {
			System.out.println("Node " + node.getId() + ": (" + nodePositions.get(node).getX() + ", " + nodePositions.get(node).getY() + ")");
		}
		// 获取节点位置并设置到节点对应的position2
		Map<String, Node> nodeMap1 = StreamApiUtils.toMap(nodes, Node::getId, s -> s);
		topoElements.forEach(s->{
			String id = s.getId();
			JSONObject jsonObject = new JSONObject();
			Node node = nodeMap1.get(id);
			Point2D point2D = nodePositions.get(node);
			jsonObject.put("x", point2D.getX());
			jsonObject.put("y", point2D.getY());
			s.setPosition2(jsonObject.toJSONString());
		});
	}

	private Topology analyzeFiles(MultipartFile file) throws IOException {
		Gson gson = new GsonBuilder()
				.setFieldNamingStrategy(field -> {
					// 将驼峰命名转换为下划线命名
					String name = field.getName();
					StringBuilder result = new StringBuilder();
					for (int i = 0; i < name.length(); i++) {
						char ch = name.charAt(i);
						if (Character.isUpperCase(ch)) {
							result.append('_').append(Character.toLowerCase(ch));
						} else {
							result.append(ch);
						}
					}
					return result.toString();
				})
				.create();
		String apiDoc = ConfigUtil.getContextProperty("ojj.apiDoc");
		String unzipDir;
		if(Boolean.parseBoolean(apiDoc)){
			unzipDir = "D:/cpms/decompress/";
		} else {
			unzipDir = "/cpms/decompress/";

		}
		File unzip = new File(unzipDir);
		Map<String, String> fileMap = FileUtil.zipDecompress(file, unzip);
		// 拓扑探测信息
		String fileName = fileMap.get("topo");
		log.info("the current filename---- [{}]",fileName);
		Map<String, Convertor> map = new HashMap<>();
		try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // 处理每行内容，这里简单打印输出
                NetworkProbeData probeData = gson.fromJson(line, NetworkProbeData.class);
				Convertor convertor = Convertor.builder().probeData(probeData).build();
				map.put(probeData.getInstanceId(), convertor);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


		// 节点服务信息
		List<ReportData> dataList = new ArrayList<>();
		String instanceId = "";
		try (BufferedReader reader = new BufferedReader(new FileReader(fileMap.get("server")))) {
			String line;
			while ((line = reader.readLine()) != null) {
				ReportData data = gson.fromJson(line, ReportData.class);
				dataList.add(data);
				instanceId = data.getInstanceId();
			}
		} catch (IOException e) {
			throw e;
		}
		Convertor convertor = map.get(instanceId);
		convertor.setDataList(dataList);

		// 配置好的服务和镜像的映射信息
		ServiceImageMap imageMap = gson.fromJson(new FileReader(fileMap.get("image")), ServiceImageMap.class);
		convertor.setImageMap(imageMap);
		Topology topo = convertor.convert();
		return topo;
	}

	private void getTopoElement(TopoElement topoElement, String id, String name, ImagesByIdVO imagesPageVO, Element element) {
		String logo = imagesPageVO.getLogo();
		topoElement.setType(imagesPageVO.getRole());
		topoElement.setIcon2(logo);
		if (imagesPageVO != null && imagesPageVO.getExtSystemInfo() != null) {
			topoElement.setSystemLanguage(imagesPageVO.getExtSystemInfo().getLanguage());
			topoElement.setSystemArchitecture(imagesPageVO.getExtSystemInfo().getSystemArchitecture());
			topoElement.setSystemType(imagesPageVO.getExtSystemInfo().getSystemType());
		}
		if (imagesPageVO != null && imagesPageVO.getRole() != null) {
			topoElement.setMaxPortNumber(imagesPageVO.getRole() == 4 || imagesPageVO.getRole() == 7 ? 1:0);
		}
		if (imagesPageVO != null && imagesPageVO.getExtVm() != null) {
			topoElement.setCpu(imagesPageVO.getExtVm().getCpu());
			topoElement.setDisk(imagesPageVO.getExtVm().getDisk());
			topoElement.setRam(imagesPageVO.getExtVm().getRam());
		}

		// 实装设备特有逻辑 ext_entity..  当前导入的镜像设备 不涉及
		// TODO 网络设备获取port—ips
		// imagesPageVO.getExtGateway().getPortConfigs().get()
		topoElement.setId(id);
		topoElement.setName(StringUtils.isEmpty(name) ? element.getId() : name);
	}


	private CpcloudElement getCpcloudElement(ImagesByIdVO imagesPageVO) {
		CpcloudElement elementObj = new CpcloudElement();
		elementObj.setRole(imagesPageVO.getRole());
		elementObj.setImageType(imagesPageVO.getImageType());
		elementObj.setImageUuid(imagesPageVO.getUuid());
		elementObj.setIsBuildIn(imagesPageVO.getIsInternal());
		elementObj.setImage(imagesPageVO.getName());
		if(null ==imagesPageVO.getExtVm() ||null == imagesPageVO.getExtVm().getExtInfo() || null == imagesPageVO.getExtVm().getExtInfo().getJSONObject("ext_dynamips")){
			elementObj.setPlatform("");
		} else {
			JSONObject ext_dynamips = imagesPageVO.getExtVm().getExtInfo().getJSONObject("ext_dynamips");
			elementObj.setPlatform(ext_dynamips.getString("platform"));

		}

		elementObj.setElectricPortNumber("simple".equals(imagesPageVO.getImageType()) ? 4 : 0);

		if (imagesPageVO != null && imagesPageVO.getExtVm() != null && imagesPageVO.getExtVm().getInitSupport()) {
			elementObj.setInitSupport(null != imagesPageVO.getExtVm().getInitSupport() ?
					imagesPageVO.getExtVm().getInitSupport() : imagesPageVO.getExtDocker().getInitSupport());
		}

		elementObj.setCategory(imagesPageVO.getCategory());
		elementObj.setCategory2(imagesPageVO.getSecondCategory());
		elementObj.setCategory3(imagesPageVO.getThirdCategory());

		JSONArray dns = new JSONArray();
		JSONObject obj1 = new JSONObject();
		obj1.put("dns", "8.8.8.8");
		dns.add(obj1);
		JSONObject obj2 = new JSONObject();
		obj2.put("dns", "114.114.114.114");
		dns.add(obj2);
		if (imagesPageVO != null && imagesPageVO.getRole() != null) {
			elementObj.setCidr(imagesPageVO.getRole() == 1 ? "192.168.1.10/24" : "");
			elementObj.setGateway(imagesPageVO.getRole() == 1 ? "192.168.1.254" : "");
			elementObj.setDnsNameservers(imagesPageVO.getRole() == 1 ? dns : new JSONArray());
		}

		elementObj.setVlanId(null);
		if(null == imagesPageVO.getExtGateway() || null == imagesPageVO.getExtGateway().getPortConfigs()){
			elementObj.setWanPortNumber(0);
			elementObj.setLanPortNumber(0);
		} else {
			List<PortConfig> portConfigs = imagesPageVO.getExtGateway().getPortConfigs();
			elementObj.setWanPortNumber((int) portConfigs.stream().filter(s -> s.getPortType() == 1).count());
			elementObj.setLanPortNumber((int) portConfigs.stream().filter(s -> s.getPortType() == 2).count());

		}

		elementObj.setWanPortType("dynamips".equals(imagesPageVO.getImageType()) ? "sfp" : "E");


		// Example of setting quick create params
		// elementObj.setQuickCreateParams(imagesByIdVO.getExtVm().getQuickCreateParams()); // Uncomment if needed

		elementObj.setIsoAttachFilename("vm".equals(imagesPageVO.getImageType()) && imagesPageVO.getExtVm().getIsoAttach() ?
				imagesPageVO.getExtVm().getIsoAttachFilename() : "undefined");
		return elementObj;
	}

}
