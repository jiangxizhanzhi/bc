package com.cyberpeace.cr.evaluation.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "靶场测评-测绘")
@RestController
//@RequiresAuthentication
@Slf4j
@RequestMapping("/cr/eval/template/scene/map/")
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
	@PostMapping("/")
	public BaseRsp<EvalSceneMapRsp> loadMap(
			@RequestParam("file") MultipartFile file
	) throws IOException {
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
		String originalFilename = file.getOriginalFilename();
		String replace = originalFilename.replace(".zip", "");
		String unzipDir = "/cpms/decompress/";
		File unzip = new File(unzipDir);
		FileUtil.zipDecompress(file, unzip);
		// 拓扑探测信息
		String fileName = unzipDir + File.separator + replace + File.separator + "topology.json";
		log.info("the current filename---- [{}]",fileName);
		NetworkProbeData probeData = gson.fromJson(new FileReader(fileName), NetworkProbeData.class);

		Convertor convertor = Convertor.builder().probeData(probeData).build();

		// 节点服务信息
		try (BufferedReader reader = new BufferedReader(new FileReader(unzipDir + File.separator + replace + File.separator + "server_data.json"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				ReportData data = gson.fromJson(line, ReportData.class);
				convertor.addData(data);
			}
		} catch (IOException e) {
			throw e;
		}

		// 配置好的服务和镜像的映射信息
		ServiceImageMap imageMap = gson.fromJson(new FileReader(unzipDir + File.separator + replace + File.separator +"image_map.json"), ServiceImageMap.class);
		convertor.setImageMap(imageMap);


		List<ImagesByIdVO> imageList = remoteSceneService.getImageList();
		Map<String, String> imagesMap = StreamApiUtils.toMap(imageList, ImagesByIdVO::getName, s -> s.getUuid());

		// 执行转换逻辑
		Topology topo = convertor.convert();

		Map<String, Element> elementMap = topo.getElementMap();
		List<String> images = elementMap.values().stream().filter(element -> element.getImage() != null).
				map(element -> imagesMap.get(element.getImage())).distinct().collect(Collectors.toList());
		List<ImagesByIdVO> imageListByuuids = remoteSceneService.getImageListByuuids(images);
		Map<String, ImagesByIdVO> imagesByIdVOMap = StreamApiUtils.toMap(imageListByuuids, ImagesByIdVO::getName, s -> s);
		List<Element> elelemts = topo.getElelemts();
		List<Port> ports = topo.getPorts();
		Map<String, List<Port>> portMap = StreamApiUtils.groupBy(ports, Port::getElement);
		List<TopoElement> topoElements = new ArrayList<>();
		List<Connections> sceneConnections = new ArrayList<>();
		List<CpcloudElement> elements = new ArrayList<>();
		JSONArray elementPorts = new JSONArray();
		HashMap<String, String> map = new HashMap<>();
		HashMap<String, ImagesByIdVO> elementPortMap = new HashMap<>();

		for(Element element:elelemts){
			TopoElement topoElement = new TopoElement();
			String id = IDGeneratorUtil.getID();
			//TODO 设备名称
			String name = element.getName();
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
			CpcloudElement elementObj = new CpcloudElement(imagesPageVO);
			elements.add(elementObj);
			topoElement.setName(name);


			List<Port> portList = portMap.get(element.getId());
			JSONArray objects = new JSONArray();
			int portIndex = 1;
			// 端口id 仿真节点 1个端口 4- image_type vm 虚拟机 container 容器 连线超出当前max_port_num  需要手动生成 自动补足
			// 网关 ext_gateway -port——configs 几条数据则 几个端口 判断连线是否超出当前端口数 超出则自动补足 区分wan lan口 port_type 2-lan 除了2都是wan口
			for(Port port:portList){
				String value = "port_" + id + "_" + portIndex;
				String portId = port.getId();
				JSONObject elementPortObj = new JSONObject();
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
				elementPortObj.put(value, item);
				elementPorts.add(elementPortObj);
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
		cpcloud.setElements(elements);
		plugins.setCpcloud(cpcloud);
		rsp.setPlugins(plugins);
		return new BaseRsp<>(rsp);
	}

}
