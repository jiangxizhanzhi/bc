package com.cyberpeace.scene.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.scene.service.PineappleService;
import com.cyberpeace.scene.service.bo.interactive.QQRes;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PineappleServiceImpl implements PineappleService {

	private String QQ_URL;

	private String LOGO_URL;

	@PostConstruct
	public void init() {

		String piHost = SystemPropUtil.getProp("PI_HOST");
		String piPort = ConfigUtil.getContextProperty("pi.port");
		String numberPath = ConfigUtil.getContextProperty("pi.qq.path");
		String logoPath = ConfigUtil.getContextProperty("pi.logo.path");

		QQ_URL = "http://" + piHost + ":" + piPort + numberPath;
		LOGO_URL = "http://" + piHost + ":" + piPort + logoPath;

		log.info("pineapple host=[{}], port=[{}]", piHost, piPort);
	}

	@Override
	public List<QQRes> listQQ() {
		List<QQRes> res = Lists.newArrayList();
		String rsp = HttpClientUtil.readPlainText(QQ_URL);
		if (StringUtils.isBlank(rsp)) {
			return res;
		}
		String[] numbers = rsp.split("\n");
		for (String number : numbers) {
			if (StringUtils.isNotBlank(number)) {
				String logoUrl = String.format(LOGO_URL, number);
				QQRes qq = new QQRes(number, logoUrl);
				res.add(qq);
			}
		}
		return res;
	}
}
