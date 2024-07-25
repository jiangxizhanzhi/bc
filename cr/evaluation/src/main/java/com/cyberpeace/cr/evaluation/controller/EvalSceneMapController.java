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
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSceneMapRsp;
import com.cyberpeace.cr.evaluation.dao.mode.*;
import com.cyberpeace.cr.evaluation.dao.mode.probe.NetworkProbeData;
import com.cyberpeace.cr.evaluation.dao.mode.probe.data.ReportData;
import com.cyberpeace.cr.evaluation.dao.mode.topo.*;
import com.cyberpeace.cr.evaluation.utils.CidrCalculator;
import com.cyberpeace.cr.evaluation.utils.NetworkUtils;
import com.cyberpeace.integratation.model.mp.*;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import edu.uci.ics.jung.algorithms.layout.FRLayout;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Api(tags = "靶场测评-测绘")
@RestController
//@RequiresAuthentication
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
		if(images.isEmpty()){
			throw new BusinessException(ErrorCode.INVALID_IMAGES);
		}
		List<ImagesByIdVO> imageListByuuids = remoteSceneService.getImageListByuuids(images);
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
			// 中台获取的镜像角色
			imagesPageVO.setRole(roleMap.get(imagesPageVO.getRole()));
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


			} else if(null != extVm.getAccessProtocols()){
				item.put("access_modes", extVm.getAccessProtocols());
				object.put("access_modes", extVm.getAccessProtocols());

			} else if(null != extDocker.getAccessProtocols()){
				item.put("access_modes", extDocker.getAccessProtocols());
				object.put("access_modes", extDocker.getAccessProtocols());

			}
			item.put("connect_mode", (imagesByIdVO.getRole() == 1 || imagesByIdVO.getRole() == 2) && !"simple".equals(imagesByIdVO.getImageType()) ? "simple":null);
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
		NetworkProbeData probeData = gson.fromJson(new FileReader(fileName), NetworkProbeData.class);

		Convertor convertor = Convertor.builder().probeData(probeData).build();

		// 节点服务信息
		try (BufferedReader reader = new BufferedReader(new FileReader(fileMap.get("server")))) {
			String line;
			while ((line = reader.readLine()) != null) {
				ReportData data = gson.fromJson(line, ReportData.class);
				convertor.addData(data);
			}
		} catch (IOException e) {
			throw e;
		}

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
		topoElement.setSystemLanguage(imagesPageVO.getExtSystemInfo().getLanguage());
		topoElement.setSystemArchitecture(imagesPageVO.getExtSystemInfo().getSystemArchitecture());
		topoElement.setSystemType(imagesPageVO.getExtSystemInfo().getSystemType());
		topoElement.setMaxPortNumber(imagesPageVO.getRole() == 4 || imagesPageVO.getRole() == 7 ? 1:0);
		topoElement.setCpu(imagesPageVO.getExtVm().getCpu());
		topoElement.setDisk(imagesPageVO.getExtVm().getDisk());
		topoElement.setRam(imagesPageVO.getExtVm().getRam());
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

		elementObj.setInitSupport(null != imagesPageVO.getExtVm().getInitSupport() ?
				imagesPageVO.getExtVm().getInitSupport() : imagesPageVO.getExtDocker().getInitSupport());

		elementObj.setCategory(imagesPageVO.getCategory());
		elementObj.setCategory2(imagesPageVO.getSecondCategory());
		elementObj.setCategory3(imagesPageVO.getThirdCategory());

		elementObj.setCidr(imagesPageVO.getRole() == 1 ? "192.168.1.10/24" : "");
		elementObj.setGateway(imagesPageVO.getRole() == 1 ? "192.168.1.254" : "");
		JSONArray dns = new JSONArray();
		JSONObject obj1 = new JSONObject();
		obj1.put("dns", "8.8.8.8");
		dns.add(obj1);
		JSONObject obj2 = new JSONObject();
		obj2.put("dns", "114.114.114.114");
		dns.add(obj2);
		elementObj.setDnsNameservers(imagesPageVO.getRole() == 1 ? dns : new JSONArray());

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

	@ApiOperation("导入设备配置")
//	@RequiresPermissions(value = { MenuConstant.CMS_CR_EVALUATION_TEMPLATE,MenuConstant. WEB_CR_EVALUATION }, logical = Logical.OR)
	@PostMapping("/config")
	public BaseRsp<EvalSceneMapRsp> loadConfig(
			@RequestParam("file") MultipartFile file
	) {
		EvalSceneMapRsp evalSceneMapRsp = new EvalSceneMapRsp();
		try {
			// 1. 将MultipartFile转换为File
			File zipFile = convertMultiPartToFile(file);

			// 2. 解压ZIP文件并遍历其中的TXT文件
			evalSceneMapRsp = unzipAndProcessFiles(zipFile);

			return new BaseRsp<>(evalSceneMapRsp);
		} catch (IOException e) {
			e.printStackTrace();
			return new BaseRsp<>(evalSceneMapRsp);
		}
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		try (FileOutputStream fos = new FileOutputStream(convFile)) {
			fos.write(file.getBytes());
		}
		return convFile;
	}

	private EvalSceneMapRsp unzipAndProcessFiles(File zipFile) throws IOException {
		EvalSceneMapRsp rsp = new EvalSceneMapRsp();
		try (ZipFile zip = new ZipFile(zipFile)) {
			Enumeration<ZipArchiveEntry> entries = zip.getEntries();
			List<DeviceVO> routerAndFirewall = remoteSceneService.getRouterAndFirewall();
			Map<String, DeviceVO> images = StreamApiUtils.toMap(routerAndFirewall, DeviceVO::getUuid, s -> s);
			DeviceVO router = images.get(CrCommonConstants.ROUTER_UUID);
			Integer routerRole = router.getRole();
			router.setRole(roleMap.get(routerRole));
			DeviceVO firewall = images.get(CrCommonConstants.FIREWALL_UUID);
			DeviceVO exchange = images.get(CrCommonConstants.EXCHANGE_UUID);
			Integer firewallRole = firewall.getRole();
			firewall.setRole(firewallRole);
			List<TopoElement> topoElements = new ArrayList<>();
			JSONObject cpElements = new JSONObject();
			JSONObject elementPorts = new JSONObject();
			List<Connections> sceneConnections;
			List<String> nodes = new ArrayList<>();
			Map<String, List<Router>> routerMap = new HashMap<>();
			while (entries.hasMoreElements()) {
				ZipArchiveEntry entry = entries.nextElement();
				String name = entry.getName();
				if (!entry.isDirectory() && name.endsWith(".txt")) {
					name = name.replace( ".txt", "");

					// 处理TXT文件，文件内容包含两种设备 路由器、防火墙 对应的信息可以从拓扑编辑器中的常用节点接口获取 该路由器与镜像路由器有区别 当前取前者
					List<Router> routers = processTxtFile(zip.getInputStream(entry), name);
					// 根据常用节点中路由器镜像信息初始化 节点信息
					TopoElement topoElement = new TopoElement();
					String id = IDGeneratorUtil.getID();
					nodes.add(id);
					routerMap.put(id, routers);
					topoElement.setName(name);
					topoElement.setId(id);
					getTopoElementByDeviceVo(topoElement, router);
					topoElement.setType(CrCommonConstants.ROUTER_ROLE);
					CpcloudElement elementObj = getCpcloudElementByDeviceDo(router);
					cpElements.put(id, elementObj);
					JSONArray objects = new JSONArray();
					// 待确认解析的文件内容包含的连线关系
					topoElement.setPorts(objects);
					topoElements.add(topoElement);

				}
			}
			// 根据设备文件中的 遍历取 设备A 与 设备B 的 接口a 、接口b 是否在同一网段 具体实现是networkutil中的方法 入参为 接口的IP 网关
			// 若出现 两台设备相同IP段就直连，三台及以上设备的话就需要新增一台交换机 设备均与交换机相连
			sceneConnections = getConnection(nodes, routerMap, topoElements, elementPorts, exchange, cpElements);
			topoElements.forEach(s-> {
				String id = s.getId();
				JSONArray ports = s.getPorts();
				CpcloudElement cpcloudElement = cpElements.getJSONObject(id).toJavaObject(CpcloudElement.class);
				cpcloudElement.setElectricPortNumber(ports.size());
				cpElements.put(id, cpcloudElement);
			});
			setElementPosition(topoElements, sceneConnections);
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
		}
		return rsp;
	}

	private List<Connections> getConnection(List<String> ids, Map<String, List<Router>> routerMap,
											List<TopoElement> topoElements, JSONObject elementPorts, DeviceVO exchange, JSONObject cpElements) throws UnknownHostException {
		Map<String, TopoElement> elementMap = StreamApiUtils.toMap(topoElements, TopoElement::getId, s -> s);
		List<Connections> connections = new ArrayList<>();
		Map<String, String> connectMap = new HashMap<>();
		Map<String, List<TopoElement>> exchangeMap = new HashMap<>();
		Map<String, List<TopoElement>> noExchangeMap = new HashMap<>();
		// 先遍历所有的设备直接的接口 根据 ip 网关计算出该接口的网段地址 根据该地址分类 一类为两个设备同网段 则这两个设备互相连线 一类为三个及以上设备同网段 则需手动添加交换机 均与交换机相连
		for(int i =0;i< ids.size();i++) {
			String id = ids.get(i);
			List<Router> routers = routerMap.get(id);
			for (int j = 0; j < ids.size(); j++) {
				if (i <= j) {
					continue;
				}
				String nextId = ids.get(j);
				List<Router> nextRouters = routerMap.get(nextId);
				for (Router router : routers) {
					List<PhysicalPort> physicalPorts = router.getPhysicalPorts();
					for (PhysicalPort physicalPort : physicalPorts) {
						String ip = physicalPort.getIp();
						String subnetMask = physicalPort.getSubnetMask();
						for (Router nextRouter : nextRouters) {
							List<PhysicalPort> ports = nextRouter.getPhysicalPorts();
							for (PhysicalPort port : ports) {
								String ip2 = port.getIp();
								String subnetMask2 = port.getSubnetMask();
								log.info("tooo  connections :[{}]--[{}]--[{}]--[{}]", ip, ip2, subnetMask, subnetMask2);
								if (NetworkUtils.sameSubnet(ip, ip2, subnetMask, subnetMask2)) {
									String address = NetworkUtils.calculateNetworkAddress(ip, subnetMask);
									TopoElement topoElement = elementMap.get(id);
									TopoElement nextTopoElement = elementMap.get(nextId);
									if (!exchangeMap.containsKey(address)) {
										List<TopoElement> devices = new ArrayList<>();
										devices.add(topoElement);
										devices.add(nextTopoElement);
										exchangeMap.put(address, devices);
									} else {
										List<TopoElement> elementList = exchangeMap.get(address);
										if (!elementList.contains(topoElement)) {
											elementList.add(topoElement);
										}
										if (!elementList.contains(nextTopoElement)) {
											elementList.add(nextTopoElement);
										}
										exchangeMap.put(address, elementList);
									}
								}
							}
						}
					}
				}
			}
			TopoElement topoElement = elementMap.get(id);
			int index = topoElements.indexOf(topoElement);
			topoElement.setRouters(routers);
			topoElements.set(index, topoElement);
		}

		// 遍历需要添加交换的 设备列表 新增一个交换机 交换机的名称默认为switch 根据所连设备数 添加端口及连线
		for (Map.Entry<String, List<TopoElement>> entry : exchangeMap.entrySet()) {
			List<TopoElement> value = entry.getValue();
			String address = entry.getKey();
			if(value.size() == 2){
				noExchangeMap.put(entry.getKey(), value);
			} else {
				TopoElement exchangeElement = new TopoElement();
				String id = IDGeneratorUtil.getID();
				exchangeElement.setName("Switch");
				exchangeElement.setId(id);
				getTopoElementByDeviceVo(exchangeElement, exchange);
				exchangeElement.setType(CrCommonConstants.EXCHANGE_ROLE);
				CpcloudElement elementObj = getCpcloudElementByDeviceDo(exchange);
				String cidr = "";
				JSONArray objects = new JSONArray();
				// 待确认解析的文件内容包含的连线关系
				exchangeElement.setPorts(objects);
				String targetIp = null;
				String targetSubnetMask = null;
				String sourceIp = null;
				for(TopoElement element:value){
					List<Router> routers = element.getRouters();
					for(Router router:routers){
						List<PhysicalPort> physicalPorts = router.getPhysicalPorts();
						Map<String, PhysicalPort> physicalPortMap = StreamApiUtils.toMap(physicalPorts, PhysicalPort::getAddress, s -> s);
						PhysicalPort physicalPort = physicalPortMap.get(address);
						if(physicalPort == null){
							continue;
						}
						targetIp = physicalPort.getIp();
						targetSubnetMask = physicalPort.getSubnetMask();
						cidr = CidrCalculator.getCidr(targetIp, targetSubnetMask);
						elementObj.setCidr(cidr);
						elementObj.setGateway(targetIp);
						List<String> address1 = NetworkUtils.calculateBroadcastAddress(targetIp, targetSubnetMask);
						sourceIp = address1.get(1);
					}
					JSONArray ports1 = element.getPorts();
					int sourcePortNum = objects.isEmpty() ? 1 : objects.size() + 1;
					String sourcePort = "port_" + id + "_electrical_" + sourcePortNum;
					int targetPortNum = ports1.isEmpty() ? 1 : ports1.size() + 1;
					String targetPort = "port_" + element.getId() + "_electrical_" + targetPortNum;
					JSONObject source = new JSONObject();
					source.put("id", sourcePort);
					source.put("name", "E0/" + sourcePortNum);
					source.put("mac_addr", "");
					source.put("ip", sourceIp);
					objects.add(source);
					exchangeElement.setPorts(objects);

					JSONObject target = new JSONObject();
					target.put("id", targetPort);
					target.put("name", "E0/" + targetPortNum);
					target.put("mac_addr", "");
					target.put("ip", targetIp);
					ports1.add(target);
					int index = topoElements.indexOf(element);
					element.setPorts(ports1);
					topoElements.set(index, element);
					Connections connScene = new Connections();
					String connId = IDGeneratorUtil.getID();
					connScene.setId(connId);
					connScene.setTarget(sourcePort);
					connScene.setSource(targetPort);
					connections.add(connScene);
				}
				topoElements.add(exchangeElement);
				cpElements.put(id, elementObj);

			}
		}

		// 遍历两两直连的设备列表 当前系统仅支持设备直接展示一条连线 所以已存在一根连线的两个设备不再新增连线及端口
		for (Map.Entry<String, List<TopoElement>> entry : noExchangeMap.entrySet()) {
			List<TopoElement> value = entry.getValue();
			TopoElement topoElement = value.get(0);
			TopoElement nextTopoElement = value.get(1);
			String id = topoElement.getId();
			String nextId = nextTopoElement.getId();
			List<Router> routers = routerMap.get(id);
			List<Router> nextRouters = routerMap.get(nextId);
			for(Router router:routers){
				List<PhysicalPort> physicalPorts = router.getPhysicalPorts();
				for(PhysicalPort physicalPort:physicalPorts){
					String ip = physicalPort.getIp();
					String subnetMask = physicalPort.getSubnetMask();
					for(Router nextRouter:nextRouters){
						List<PhysicalPort> ports = nextRouter.getPhysicalPorts();
						for(PhysicalPort port:ports) {
							String ip2 = port.getIp();
							String subnetMask2 = port.getSubnetMask();
							log.info("tooo  connections :[{}]--[{}]--[{}]--[{}]", ip, ip2, subnetMask, subnetMask2);
							if(NetworkUtils.sameSubnet(ip, ip2, subnetMask, subnetMask2)){
								if(connectMap.containsKey(id) && connectMap.get(id).equals(nextId)){
									continue;
								}
								connectMap.put(id, nextId);
								log.info("connections :[{}]--[{}]--[{}]--[{}]", ip, ip2, subnetMask, subnetMask2);
								int index1 = topoElements.indexOf(topoElement);
								int index = topoElements.indexOf(nextTopoElement);
								JSONArray ports1 = topoElement.getPorts();
								JSONArray ports2 = nextTopoElement.getPorts();
								int sourcePortNum = ports1.isEmpty() ? 1 : ports1.size() + 1;
								String sourcePort = "port_" + id + "_electrical_" + sourcePortNum;
								int targetPortNum = ports2.isEmpty() ? 1 : ports2.size() + 1;
								String targetPort = "port_" + nextId + "_electrical_" + targetPortNum;
								JSONObject source = new JSONObject();
								source.put("id", sourcePort);
								source.put("name", "E0/" + sourcePortNum);
								source.put("mac_addr", "");
								source.put("ip", ip);
								ports1.add(source);
								topoElement.setPorts(ports1);

								JSONObject target = new JSONObject();
								target.put("id", targetPort);
								target.put("name", "E0/" + targetPortNum);
								target.put("mac_addr", "");
								target.put("ip", ip2);
								ports2.add(target);
								nextTopoElement.setPorts(ports2);
								topoElements.set(index1, topoElement);
								topoElements.set(index, nextTopoElement);

								JSONObject targetObj = new JSONObject();
								target.put("connect_mode", (topoElement.getType() == 1 || topoElement.getType() == 2)  ? "simple":null);
								target.put("firewall_rules", null);
								target.put("logic_network_id", "");
								target.put("resource_id", "");

								elementPorts.put(targetPort, targetObj);
								elementPorts.put(sourcePort, targetObj);

								Connections connScene = new Connections();
								String connId = IDGeneratorUtil.getID();
								connScene.setId(connId);
								connScene.setTarget(sourcePort);
								connScene.setSource(targetPort);
								connections.add(connScene);

							}
						}
					}
				}
			}
		}

		return connections;
	}

	private CpcloudElement getCpcloudElementByDeviceDo(DeviceVO router) {
		// 填充信息规则参照 --场景靶标字段转换关系.js
		CpcloudElement elementObj = new CpcloudElement();
		Integer role = router.getRole();
		elementObj.setRole(role);
		elementObj.setImageType(router.getImageType());
		elementObj.setImageUuid(router.getUuid());
		elementObj.setIsBuildIn(router.getIsInternal());
		elementObj.setImage(router.getName());
		elementObj.setPlatform("");
		elementObj.setElectricPortNumber("simple".equals(router.getImageType()) ? 4 : 0);
		elementObj.setInitSupport(false);
		elementObj.setCategory(router.getCategory());
		elementObj.setCategory2(router.getSecondCategory());
		elementObj.setCategory3(router.getThirdCategory());

		elementObj.setCidr(role == 1 ? "192.168.1.10/24" : "");
		elementObj.setGateway(role == 1 ? "192.168.1.254" : "");
		JSONArray dns = new JSONArray();
		JSONObject obj1 = new JSONObject();
		obj1.put("dns", "8.8.8.8");
		dns.add(obj1);
		JSONObject obj2 = new JSONObject();
		obj2.put("dns", "114.114.114.114");
		dns.add(obj2);
		elementObj.setDnsNameservers(role == 1 ? dns : new JSONArray());

		elementObj.setVlanId(null);
		elementObj.setWanPortNumber(0);
		elementObj.setLanPortNumber(0);

		elementObj.setWanPortType("E");

		// Example of setting quick create params
		// elementObj.setQuickCreateParams(imagesByIdVO.getExtVm().getQuickCreateParams()); // Uncomment if needed
		elementObj.setIsoAttachFilename("undefined");

		return elementObj;

	}

	private void getTopoElementByDeviceVo(TopoElement topoElement, DeviceVO router) {
		String logo = router.getLogo();
		topoElement.setIcon2(logo);
		Integer role = router.getRole();
		topoElement.setType(role);
		topoElement.setMaxPortNumber(role == 4 || role == 7 ? 1:0);
	}

	private List<Router> processTxtFile(InputStream inputStream, String name) throws IOException {

		// 这里可以实现对TXT文件内容的处理逻辑，例如读取内容或者解析内容等
		try {
			String inputStreamString = inputStreamToString(inputStream);
//			String regex = "interface\\s+(\\S+)\\s+\\n\\s+ip address\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})";
			String ipAddressPattern = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b";
			String[] interfaces = inputStreamString.split("router ospf")[0].split("interface");
			List<Router> routers = new ArrayList<>();
			for(String inter:interfaces){
				if(StringUtils.isEmpty(inter)){
					continue;
				}
				// 正则匹配出文件中下列接口的 IP和网关地址
				String regex = "(GigabitEthernet|FastEthernet|Serial|TenGigabitEthernet)[0-9]+(/[0-9]+)*$";
				String[] split = inter.split("\n");
				Router router = new Router();
				List<PhysicalPort> physicalPorts = new ArrayList<>();
				for(String item:split){
					PhysicalPort physicalPort = new PhysicalPort();
					String interfaceName = "";
					if(item.contains("ip address")){
						String replace = item.replace(" ip address ", "");
						String[] strings = replace.split(" ");
						String ipAddress = strings[0];
						String subnetMask = strings[1];
						physicalPort.setIp(ipAddress);
						physicalPort.setSubnetMask(subnetMask);
						String address = NetworkUtils.calculateNetworkAddress(ipAddress, subnetMask);
						physicalPort.setAddress(address);
						physicalPorts.add(physicalPort);
					} else if(item.contains("description")){
						continue;
					} else {
						item = item.replace(" ", "");
						if(!item.matches(regex)){
							continue;
						}
						interfaceName = item;
						router.setInterfaceName(interfaceName);
					}
				}
				router.setPhysicalPorts(physicalPorts);
				if(null != router.getInterfaceName()){
					routers.add(router);
				}
			}
			return routers;
			// 设备名称-name 设备接口 interfaceName
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
	}

	public static String inputStreamToString(InputStream inputStream) throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br
					 = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}


}
