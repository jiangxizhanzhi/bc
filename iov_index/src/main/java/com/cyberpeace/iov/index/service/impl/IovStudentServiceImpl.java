package com.cyberpeace.iov.index.service.impl;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.iov.constant.IovConstants;
import com.cyberpeace.iov.dao.entity.LastRawPacket;
import com.cyberpeace.iov.dao.mapper.LastRawPacketMapper;
import com.cyberpeace.iov.index.service.IovStudentService;
import com.cyberpeace.iov.index.service.bo.student.RawPacket;
import com.cyberpeace.iov.index.util.TSharkInvoker;
import com.cyberpeace.iov.util.IovHttpClientUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IovStudentServiceImpl implements IovStudentService {

	private static String SNIFF_WIFI_URL;

	private static String SNIFF_BLUETOOTH_URL;

	private static String DOWNLOAD_PATH;

	@Autowired
	private LastRawPacketMapper lastRawPacketMapper;

	@PostConstruct
	public void init() {
		String KA_HOST = SystemPropUtil.getProp("KA_HOST");
		if (StringUtils.isBlank(KA_HOST) || !KA_HOST.startsWith("http")) {
			log.error("KA_HOST [{}] not starts with http!", KA_HOST);
			throw new BusinessException("KA_HOST not set");
		}
		SNIFF_WIFI_URL = KA_HOST + ":" + SystemPropUtil.getProp("KA_WIFI_PORT")
				+ ConfigUtil.getContextProperty("ka.sniff.wifi");
		SNIFF_BLUETOOTH_URL = KA_HOST + ":" + SystemPropUtil.getProp("KA_BT_PORT")
				+ ConfigUtil.getContextProperty("ka.sniff.bluetooth");
		DOWNLOAD_PATH = ConfigUtil.getContextProperty("cr.local.file.prefix") + "blf";
		log.info("ka host is =[{}]", KA_HOST);
	}

	@Override
	public List<String> sniffBluetooth() {
		String fileName = getDownloadPath();
		JSONObject body = new JSONObject();
		body.put("second", 10);
		IovHttpClientUtil.rawPostFile(SNIFF_BLUETOOTH_URL, body, fileName);
		List<String> rsp = Lists.newArrayList();
		try {
			rsp = TSharkInvoker.read(fileName);
			if(!rsp.isEmpty()){
				lastRawPacketMapper.updatePacket(IovConstants.PacketType.BLUETOOTH, JsonUtil.arrayToString(rsp));
			}
		} catch (Exception e) {
			log.error("get pcap parse result error", e);
		}
		return rsp;
	}

	@Override
	public JSONArray sniffWifi() {
		JSONArray res = IovHttpClientUtil.postJson(SNIFF_WIFI_URL, null);
		if (null != res && !res.isEmpty()) {
			lastRawPacketMapper.updatePacket(IovConstants.PacketType.WIFI, res.toJSONString());
		}
		return res;
	}

	@Override
	public RawPacket getLatestPacket() {
		RawPacket res = new RawPacket();
		List<LastRawPacket> packetList = lastRawPacketMapper.selectList(null);
		for (LastRawPacket rawPacket : packetList) {
			if (Objects.equals(rawPacket.getType(), IovConstants.PacketType.CAN)) {
				res.setCan(JsonUtil.stringToArray(rawPacket.getRaw()));
			}
			if (Objects.equals(rawPacket.getType(), IovConstants.PacketType.WIFI)) {
				res.setWifi(JsonUtil.stringToArray(rawPacket.getRaw()));
			}
			if (Objects.equals(rawPacket.getType(), IovConstants.PacketType.BLUETOOTH)) {
				res.setBluetooth(JsonUtil.toJsonObjectArray(rawPacket.getRaw(), String.class));
			}
		}
		return res;
	}

	private String getDownloadPath() {
		final UserInfo userContext = UserInfoContext.getUserContext();
		final String userId = userContext.getUserId();
		final String formatter = DateUtil.generateTimeNamedFileName();
		return DOWNLOAD_PATH + "/" + userId + "/" + formatter + "_bt.pcap";
	}
}
