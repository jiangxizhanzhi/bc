package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.common.util.SystemPropUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.Socket;

@Slf4j
@Service
public class RemoteServerEnvService {

	private String OFFICE_SERVER_HOST;

	private String OFFICE_SERVER_PORT;

	private String OFFICE_SERVER_URL;

	private String FILE_SERVER_HOST;

	private String FILE_SERVER_PORT;

	private String FILE_SERVER_URL;

	public String getOfficeHost() {
		return OFFICE_SERVER_URL;
	}

    public Boolean checkOffice() {

		// 如果配置了不检查office服务，则直接返回true
		if (!"true".equals(SystemPropUtil.getProp("CHECK_OFFICE"))){
			return true;
		}

        // 提取host
        String host;
        if (OFFICE_SERVER_HOST.startsWith("http")) {
            host = OFFICE_SERVER_HOST.split("//")[1];
        } else {
            host = OFFICE_SERVER_HOST;
        }

        // 提取端口
        Integer port = Integer.parseInt(OFFICE_SERVER_PORT);

        // 检查Office服务是否可用
		try (Socket socket = new Socket(host, port)) {
            return true;
        } catch (Exception e) {
            log.error("Office server is not available", e);
            return false;
        }
    }

	public String getFileServerHost() {
		return FILE_SERVER_URL;
	}

	@PostConstruct
	public void init() {
		OFFICE_SERVER_HOST = SystemPropUtil.getProp("OFFICE_SERVER_HOST");
		OFFICE_SERVER_PORT = SystemPropUtil.getProp("OFFICE_SERVER_PORT");

		if (StringUtils.isNotBlank(OFFICE_SERVER_HOST) && OFFICE_SERVER_HOST.startsWith("http")) {
			OFFICE_SERVER_URL = OFFICE_SERVER_HOST + ":" + OFFICE_SERVER_PORT;
		} else {
			OFFICE_SERVER_URL = "http://" + OFFICE_SERVER_HOST + ":" + OFFICE_SERVER_PORT;
		}

		log.info("Office server url=[{}]", OFFICE_SERVER_URL);

		FILE_SERVER_HOST = SystemPropUtil.getProp("FILE_SERVER_HOST");
		FILE_SERVER_PORT = SystemPropUtil.getProp("FILE_SERVER_PORT");
		if (StringUtils.isNotBlank(FILE_SERVER_HOST) && FILE_SERVER_HOST.startsWith("http")) {
			FILE_SERVER_URL = FILE_SERVER_HOST + ":" + FILE_SERVER_PORT;
		} else {
			FILE_SERVER_URL = "http://" + FILE_SERVER_HOST + ":" + FILE_SERVER_PORT;
		}
		log.info("File server url=[{}]", FILE_SERVER_URL);
	}

}
