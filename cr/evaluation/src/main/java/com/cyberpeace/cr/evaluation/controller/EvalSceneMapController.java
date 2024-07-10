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
import com.cyberpeace.integratation.model.mp.*;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

	@ApiOperation("导入测绘拓扑")
//	@RequiresPermissions(value = { MenuConstant.CMS_CR_EVALUATION_TEMPLATE,MenuConstant. WEB_CR_EVALUATION }, logical = Logical.OR)
	@PostMapping("/map")
	public BaseRsp<EvalSceneMapRsp> loadMap(
			@RequestParam("file") MultipartFile file
	) throws IOException {
		Topology topo = analyzeFiles(file);


		List<ImagesByIdVO> imageList = remoteSceneService.getImageList();
		Map<String, String> imagesMap = StreamApiUtils.toMap(imageList, ImagesByIdVO::getName, s -> s.getUuid());

		// 执行转换逻辑

		Map<String, Element> elementMap = topo.getElementMap();
		List<String> images = elementMap.values().stream().filter(element -> element.getImage() != null).
				map(element -> imagesMap.get(element.getImage())).distinct().collect(Collectors.toList());
		if(images.isEmpty()){
			throw new BusinessException(ErrorCode.INVALID_IMAGES);
		}
		List<ImagesByIdVO> imageListByuuids = remoteSceneService.getImageListByuuids(images);
		Map<String, ImagesByIdVO> imagesByIdVOMap = StreamApiUtils.toMap(imageListByuuids, ImagesByIdVO::getName, s -> s);
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
			imagesPageVO.setRole(roleMap.get(imagesPageVO.getRole()));
			elementPortMap.put(id, imagesPageVO);
			getTopoElement(topoElement, id, name, imagesPageVO);
			CpcloudElement elementObj = getCpcloudElement(imagesPageVO);
			cpElements.put(id, elementObj);


			List<Port> portList = portMap.get(element.getId());
			JSONArray objects = new JSONArray();
			int portIndex = 1;
			// 端口id 仿真节点 1个端口 4- image_type vm 虚拟机 container 容器 连线超出当前max_port_num  需要手动生成 自动补足
			// 网关 ext_gateway -port——configs 几条数据则 几个端口 判断连线是否超出当前端口数 超出则自动补足 区分wan lan口 port_type 2-lan 除了2都是wan口
			for(Port port:portList){
				String value = "port_" + id + "_" + portIndex;
				String portId = port.getId();
				JSONObject item = new JSONObject();
				ImagesByIdVO imagesByIdVO = elementPortMap.get(id);
				ExtGateway gateway = imagesByIdVO.getExtGateway();
				ExtVm extVm = imagesByIdVO.getExtVm();
				ExtDocker extDocker = imagesByIdVO.getExtDocker();
				if(null != gateway){
					item.put("access_mode", gateway);

				} else if(null != extVm.getAccessProtocols()){
					item.put("access_mode", extVm.getAccessProtocols());
				} else if(null != extDocker.getAccessProtocols()){
					item.put("access_mode", extDocker.getAccessProtocols());
				}
				item.put("connect_mode", (imagesByIdVO.getRole() == 1 || imagesByIdVO.getRole() == 2) && !"simple".equals(imagesByIdVO.getImageType()) ? "simple":null);
				item.put("firewall_rules", null);
				item.put("logic_network_id", "");
				item.put("resource_id", "");
				elementPorts.put(value, item);
				map.put(portId, value);
				JSONObject object = new JSONObject();
				object.put("id", value);
				object.put("name", port.getName());
				object.put("mac_addr", "");
				object.put("ip", null);
				objects.add(object);
				portIndex++;
			}
			topoElement.setPorts(objects);
			topoElements.add(topoElement);

		}

		List<Connection> connections = topo.getConnections();
		for(Connection conn:connections){
			String from = conn.getFrom();
			String to = conn.getTo();
			// 连线两端找不到对应端口 则跳过不展示该连线
			if(!map.containsKey(from) || !map.containsKey(to)){
				continue;
			}
			String source = map.get(from);
			String target = map.get(to);
			Connections connScene = new Connections();
			String id = IDGeneratorUtil.getID();
			connScene.setId(id);
			connScene.setSource(source);
			connScene.setTarget(target);
			sceneConnections.add(connScene);
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

	private void getTopoElement(TopoElement topoElement, String id, String name, ImagesByIdVO imagesPageVO) {
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
		// TODO 实装设备特有逻辑 ext_entity..
		// TODO 网络设备获取port—ips
		// imagesPageVO.getExtGateway().getPortConfigs().get()
		topoElement.setId(id);
		topoElement.setName(StringUtils.isEmpty(name) ? imagesPageVO.getName() : name);
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
			List<String> uuids = new ArrayList<>();
			uuids.add(CrCommonConstants.ROUTER_UUID);
			uuids.add(CrCommonConstants.FIREWALL_UUID);
			List<DeviceVO> routerAndFirewall = remoteSceneService.getRouterAndFirewall();
			Map<String, DeviceVO> images = StreamApiUtils.toMap(routerAndFirewall, DeviceVO::getUuid, s -> s);
			DeviceVO router = images.get(CrCommonConstants.ROUTER_UUID);
			router.setRole(roleMap.get(router.getRole()));
			DeviceVO firewall = images.get(CrCommonConstants.FIREWALL_UUID);
			firewall.setRole(firewall.getRole());
			List<TopoElement> topoElements = new ArrayList<>();
			JSONObject cpElements = new JSONObject();
			JSONObject elementPorts = new JSONObject();
			List<Connections> sceneConnections = new ArrayList<>();

			HashMap<String, DeviceVO> elementPortMap = new HashMap<>();
			while (entries.hasMoreElements()) {
				ZipArchiveEntry entry = entries.nextElement();
				String name = entry.getName();
				if (!entry.isDirectory() && name.endsWith(".txt")) {
					name = name.replace( ".txt", "");
					// 处理TXT文件，这里可以写你的业务逻辑
					processTxtFile(zip.getInputStream(entry), name, router);
					TopoElement topoElement = new TopoElement();
					String id = IDGeneratorUtil.getID();
					topoElement.setName(name);
					topoElement.setId(id);
					getTopoElementByDeviceVo(topoElement, name, router);
					elementPortMap.put(id, router);
					CpcloudElement elementObj = getCpcloudElementByDeviceDo(router);
					cpElements.put(id, elementObj);
					JSONArray objects = new JSONArray();
					int portIndex = 1;
					// 待确认解析的文件内容包含的连线关系
					topoElement.setPorts(objects);
					topoElements.add(topoElement);

				}
			}
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

	private CpcloudElement getCpcloudElementByDeviceDo(DeviceVO router) {
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

	private void getTopoElementByDeviceVo(TopoElement topoElement, String name, DeviceVO router) {
		String logo = router.getLogo();
		topoElement.setIcon2(logo);
		Integer role = router.getRole();
		topoElement.setType(role);
		topoElement.setMaxPortNumber(role == 4 || role == 7 ? 1:0);
	}

	private void processTxtFile(InputStream inputStream, String name, DeviceVO router) throws IOException {

		// 这里可以实现对TXT文件内容的处理逻辑，例如读取内容或者解析内容等
		try {
			String inputStreamString = inputStreamToString(inputStream);
//			String regex = "interface\\s+(\\S+)\\s+\\n\\s+ip address\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})";
			String ipAddressPattern = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b";
			String[] interfaces = inputStreamString.split("router ospf")[0].split("interface");
			for(String inter:interfaces){
				if(StringUtils.isEmpty(inter)){
					continue;
				}
				String[] split = inter.split("\n");
				Map<String, List<Router>> routerMap = new HashMap<>();
				for(String item:split){
					List<Router> routers = new ArrayList<>();
					String interfaceName = "";
					if(item.contains("ip address")){
						Router router1 = new Router();
						String replace = item.replace(" ip address ", "");
						String[] strings = replace.split(" ");
						String ipAddress = strings[0];
						String subnetMask = strings[1];
						router1.setIp(ipAddress);
						router1.setSubnetMask(subnetMask);
						routers.add(router1);
					} else if(item.contains("description")){
						continue;
					} else {
						interfaceName = item;
					}
					routerMap.put(interfaceName, routers);
				}

			}
			// 设备名称-name 设备接口 interfaceName
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isInSameSubnet(String ipAddress1, String ipAddress2, String subnetMask1, String subnetMask2) {
		// 转换IP地址为整数
		int ip1 = ipToInt(ipAddress1);
		int ip2 = ipToInt(ipAddress2);

		// 转换子网掩码为整数
		int mask1 = ipToInt(subnetMask1);
		int mask2 = ipToInt(subnetMask2);

		// 按位与运算
		return (ip1 & mask1) == (ip2 & mask2);
	}

	// 将IP地址转换为整数形式
	private int ipToInt(String ipAddress) {
		String[] octets = ipAddress.split("\\.");
		int result = 0;
		for (int i = 0; i < octets.length; i++) {
			result |= (Integer.parseInt(octets[i]) << (24 - 8 * i));
		}
		return result;
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
