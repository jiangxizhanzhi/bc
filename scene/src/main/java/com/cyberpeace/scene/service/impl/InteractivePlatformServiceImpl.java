package com.cyberpeace.scene.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.probe.RDPProbe;
import com.cyberpeace.common.probe.SMTPProbe;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.scene.constant.enums.ImageRoleEnum;
import com.cyberpeace.scene.constant.enums.InteractiveScenarioEnum;
import com.cyberpeace.scene.constant.enums.SceneEnum;
import com.cyberpeace.scene.dao.entity.*;
import com.cyberpeace.scene.dao.mapper.ConnectionMapper;
import com.cyberpeace.scene.dao.mapper.ImageMapper;
import com.cyberpeace.scene.dao.mapper.NodeMapper;
import com.cyberpeace.scene.dao.mapper.SceneMapper;
import com.cyberpeace.scene.service.*;
import com.cyberpeace.scene.service.bo.NetAccessMode;
import com.cyberpeace.scene.service.bo.interactive.*;
import com.cyberpeace.scene.service.bo.req.CreateInstanceReq;
import com.cyberpeace.scene.service.bo.res.CreateInstanceRes;
import com.cyberpeace.scene.util.JavaxJavaMailClient;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class InteractivePlatformServiceImpl implements InteractivePlatformService {

	@Autowired
	private NetworkService networkService;

	@Autowired
	private HostService hostService;

	@Autowired
	private GuacamoleService guacamoleService;

	@Autowired
	private ImageMapper imageMapper;

	@Autowired
	private NodeMapper nodeMapper;

	@Autowired
	private NodeService nodeService;

	@Autowired
	private SceneMapper sceneMapper;

	@Autowired
	private SceneService sceneService;

	@Autowired
	private ConnectionMapper connectionMapper;


	@Autowired
	private PineappleService pineappleService;

	@Autowired
	@Qualifier("asyncTaskExecutor")
	private Executor executor;

	@Override
	public void startScene(Integer scenario) {
		// 1. stop old
		stopScene(scenario);
		// 2. create new scene
		SceneEntity scene = null;
		// 勒索病毒场景不重复创建
		if (scenario.equals(InteractiveScenarioEnum.RANSOMWARE.getCode())) {
			scene = sceneMapper.selectByScenario(scenario);
		}
		if (null == scene) {
			// 2. create new scene
			String sceneId = IDGeneratorUtil.getID();
			scene = new SceneEntity();
			scene.setScenario(scenario);
			scene.setSceneName(
					EnumUtils.getEnumDescByCode(scenario, InteractiveScenarioEnum.class));
			scene.setResourceId(sceneId);
		}
		scene.setSceneStatus(SceneEnum.CREATE.getCode());
		sceneService.saveOrUpdate(scene);
		try {
			// create network with random id
			NetworkEntity net = networkService.create(scene);
			// select topo, get images
			List<ImageEntity> images = imageMapper.selectByScenario(scenario);
			for (ImageEntity image : images) {
				// create node with rdp proxy
				NodeEntity node = createInstance(image, net);
			}
		} catch (Exception e) {
			log.error("Scene start failed,scene:{}, error:{}", scene.getSceneName(), e.getMessage(),
					e);
			sceneMapper.updateSceneStatus(scene.getResourceId(), SceneEnum.ERROR.getCode());
			throw new BusinessException(ErrorCode.FAILED_TO_CREATE_SCENE, e.getMessage());
		}
		// save scene
		sceneMapper.updateSceneStatus(scene.getResourceId(), SceneEnum.ACTIVE.getCode());
		log.info("start scene end : {}", scene.getResourceId());
	}

	@Override
	public void stopScene(Integer scenario) {
		// select scene by type
		SceneEntity scene = sceneMapper.selectByScenario(scenario);
		if (null == scene) {
			return;
		}
		// delete if exist
		sceneService.destroyScene(scene.getResourceId());
		// delete connection
		deleteConnectionBySceneId(scene.getResourceId());
		// delete mail
		if (scenario.equals(InteractiveScenarioEnum.RANSOMWARE.getCode())) {
			deleteMail();
		}
	}

	@Override
	public UserLinkRsp getUserLink(Integer scenario) {
		SceneEntity scene = getSceneByScenario(scenario);
		List<NodeEntity> nodeList = nodeMapper.selectBySid(scene.getResourceId());
		UserLinkRsp rsp = new UserLinkRsp();
		rsp.setScenario(scenario);
		for (NodeEntity node : nodeList) {
			// 不创建邮件服务器的用户连接
			if (node.getType().equals(ImageRoleEnum.SERVER.getCode())) {
				continue;
			}
			UserConnectionRes connectionRes = getUserConnection(node);
			if (node.getType().equals(ImageRoleEnum.HACKER.getCode())) {
				rsp.setHacker(connectionRes.getUrl());
			} else if (node.getType().equals(ImageRoleEnum.VICTIM.getCode())) {
				rsp.setVictim(connectionRes.getUrl());
			}
		}
		return rsp;
	}

	@Override
	public void checkLink(Integer scenario) {
		SceneEntity scene = getSceneByScenario(scenario);
		List<NodeEntity> nodeList = nodeMapper.selectBySid(scene.getResourceId());
		// 先检查一般的机器，再检查邮件服务器
		nodeList = nodeList.stream().sorted(Comparator.comparing(NodeEntity::getType))
				.collect(Collectors.toList());
		for (NodeEntity node : nodeList) {
			// 检测邮件服务器
			if (node.getType().equals(ImageRoleEnum.SERVER.getCode())) {
				Access access = getSMTPAccess(node);
				SMTPProbe smtpProbe = new SMTPProbe(access.getHost(), access.getHostPort());
				if (!smtpProbe.probe()) {
					throw new BusinessException(ErrorCode.MAIL_SERVER_NOT_READY);
				}
			} else {
				Access access = node.getAccessModes().get(0);
				RDPProbe rdpProbe = new RDPProbe(access.getHost(), access.getHostPort());
				if (!rdpProbe.probe()) {
					throw new BusinessException(ErrorCode.SCENARIO_NOT_READY);
				}
			}
		}
	}

	@Override
	public void sendMail(String text) {

		try {
			JavaxJavaMailClient.sendMail(getMailPort(), text);
		} catch (Exception e) {
			log.error("failed to send mail", e);
			throw new BusinessException(ErrorCode.FAILED_TO_SEND_MAIL);
		}
	}

	@Override
	public void sendDecryptMail() {
		try {
			JavaxJavaMailClient.sendDecryptMail(getMailPort());
		} catch (Exception e) {
			log.error("failed to send decrypt mail", e);
			throw new BusinessException(ErrorCode.FAILED_TO_SEND_MAIL);
		}
	}

	@Override
	public void snapShotAll() {
		executor.execute(() -> {
			boolean flag = false;
			int cnt = 0;
			// 10秒检测一次，300s后停止,最多检查30次后放弃
			while (!flag && cnt < 30) {
				try {
					Thread.sleep(10_000);
					checkLink(InteractiveScenarioEnum.RANSOMWARE.getCode());
					checkLink(InteractiveScenarioEnum.U_DISK.getCode());
					flag = true;
				} catch (Exception e) {
					cnt += 1;
				}
			}

			if (flag) {
				List<String> nodeNames = nodeMapper.selectAllNames();
				hostService.createSnapshot(nodeNames);
			}
		});
	}

	@Override
	public List<QQRes> getQQRes() {
		return pineappleService.listQQ();
	}

	@Override
	public void recover(Integer scenario) {
		SceneEntity scene = getSceneByScenario(scenario);
		List<NodeEntity> nodes = nodeMapper.selectBySid(scene.getResourceId());
		List<String> nodeNames = StreamApiUtils.map(nodes, NodeEntity::getName);
		hostService.revertSnapshot(nodeNames);
	}

	@Override
	public void clearAll() {
		nodeService.clear();
		networkService.clear();
		clearConnection();
		sceneMapper.clear();
	}

	private SceneEntity getSceneByScenario(Integer scenario) {
		SceneEntity scene = sceneMapper.selectByScenario(scenario);
		if (null == scene) {
			throw new BusinessException(ErrorCode.SCENARIO_NOT_EXIST);
		}
		if (!scene.getSceneStatus().equals(SceneEnum.ACTIVE.getCode())) {
			throw new BusinessException(ErrorCode.SCENARIO_NOT_READY);
		}
		return scene;
	}

	@Override
	public void deleteMail() {
		try {
			JavaxJavaMailClient.deleteMail(getPopPort());
		} catch (Exception e) {
			log.error("failed to delete mail", e);
		}
	}

	private Integer getMailPort() {
		SceneEntity scene = getSceneByScenario(InteractiveScenarioEnum.RANSOMWARE.getCode());
		NodeEntity node = nodeMapper.selectBySceneAndType(scene.getResourceId(),
				ImageRoleEnum.SERVER.getCode());
		Integer port = getSmtpPort(node);
		if (null == port) {
			log.error("port mapping not established");
			throw new BusinessException(ErrorCode.FAILED_TO_SEND_MAIL);
		}
		return port;
	}

	private NodeEntity createInstance(ImageEntity image, NetworkEntity network) {
		// 勒索病毒场景不重复创建
		if (image.getType().equals(ImageRoleEnum.SERVER.getCode())) {
			NodeEntity node = nodeMapper.selectBySceneAndType(network.getSid(), image.getType());
			if (null != node) {
				return node;
			}
		}

		// 1. save node entity
		NodeEntity entity = new NodeEntity();
		entity.setResourceId(IDGeneratorUtil.getID());
		entity.setSid(network.getSid());
		// hacker or victim
		entity.setType(image.getType());
		entity.setStatus(SceneEnum.CREATE.getCode());
		entity.setNid(image.getResourceId());
		entity.setScenario(image.getScenario());

		nodeMapper.insert(entity);

		// 2.create node in cp cloud
		try {
			// 创建虚拟机
			CreateInstanceRes rsp = hostService.create(new CreateInstanceReq(image, network));
			entity.setInstanceId(rsp.getUuid());
			entity.setName(rsp.getInstanceName());
			entity.setStatus(SceneEnum.ACTIVE.getCode());

			// 记录端口映射
			List<Access> accessList = image.getAccessModes();
			for (NetAccessMode mode : rsp.getNets().get(0).getAccess_modes()) {
				Integer port = mode.getPort();
				for (Access access : accessList) {
					if (access.getPort().equals(port)) {
						access.setHostPort(mode.getPort_mapping().getHost_port());
						access.setHost(mode.getPort_mapping().getHost());
					}
				}
				entity.setIp(mode.getPort_mapping().getInstance_ip());
			}
			entity.setAccess(JsonUtil.arrayToString(accessList));

		} catch (Exception e) {
			entity.setStatus(SceneEnum.ERROR.getCode());
			throw e;
		} finally {
			// 3.update entity
			nodeMapper.updateById(entity);
		}
		return entity;
	}

	private UserConnectionRes getUserConnection(NodeEntity node) {
		ConnectionEntity connection = connectionMapper.selectByNodeId(node.getResourceId());
		if (null == connection) {
			// create and save connection
			connection = createConnection(node);
		}
		UserConnectionRes res = guacamoleService.getUserLink(connection.getConnectionId());
		return res;
	}

	private ConnectionEntity createConnection(NodeEntity node) {
		ConnectionRes res = guacamoleService.createConnection(node);
		ConnectionEntity connection = new ConnectionEntity();
		connection.setConnectionId(res.getConnectionId());
		connection.setScenario(node.getScenario());
		connection.setNodeId(node.getResourceId());
		connection.setSceneId(node.getSid());
		connectionMapper.insert(connection);
		return connection;
	}

	private void deleteConnectionBySceneId(String sceneId) {
		List<ConnectionEntity> connections = connectionMapper.selectBySceneId(sceneId);
		for (ConnectionEntity connection : connections) {
			try {
				guacamoleService.closeConnection(connection.getConnectionId());
			} catch (Exception e) {

			}
		}
		connectionMapper.deleteBySceneId(sceneId);
	}

	private void clearConnection() {
		List<ConnectionEntity> connections = connectionMapper.selectAll();
		for (ConnectionEntity connection : connections) {
			guacamoleService.closeConnection(connection.getConnectionId());
		}
		connectionMapper.clear();
	}

	private Access getSMTPAccess(NodeEntity node) {
		for (Access access : node.getAccessModes()) {
			if (access.getProtocol().equals(SMTPProbe.PROTOCOL)) {
				return access;
			}
		}
		throw new BusinessException(ErrorCode.MAIL_SERVER_NOT_READY);
	}

	private Integer getSmtpPort(NodeEntity node) {
		Access access = getSMTPAccess(node);
		return access.getHostPort();
	}

	private Integer getPopPort() {
		SceneEntity scene = getSceneByScenario(InteractiveScenarioEnum.RANSOMWARE.getCode());
		NodeEntity node = nodeMapper.selectBySceneAndType(scene.getResourceId(),
				ImageRoleEnum.SERVER.getCode());
		for (Access access : node.getAccessModes()) {
			if (access.getProtocol().equals("pop3")) {
				return access.getHostPort();
			}
		}
		throw new BusinessException(ErrorCode.MAIL_SERVER_NOT_READY);
	}

}
