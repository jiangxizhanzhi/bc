package com.cyberpeace.cr.hld.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.FtpUploadUtil;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomSceneMapper;
import com.cyberpeace.integratation.model.mp.MpLogFile;
import com.cyberpeace.integratation.service.RemoteSceneService;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时发送ftp日志
 */
@Slf4j
@Component
public class FtpScheduledJob {

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private HldRoomSceneMapper hldRoomSceneMapper;

	@Scheduled(cron = "0 0 1 * * ?")
	public void logsFtpTrans() {

		log.info("sjk log files ftp start!");

		List<JSONObject> scenes = remoteSceneService.getActiveScenes();

		log.info("active scenes is:     " + scenes.toString());
		for (JSONObject scene : scenes) {
			String sceneId = scene.getString("id");
			log.info("sceneId:" + sceneId + ",start ftp job!");
			try {
				List<MpLogFile> logFiles = remoteSceneService.getMpSceneFiles(sceneId,
						ConfigUtil.getContextProperty("cr.ftp.file.names"));

				log.info("log lists:" + logFiles.toString());
				for (MpLogFile logFile : logFiles) {
					String roomId = hldRoomSceneMapper.selectByMpSceneId(sceneId).getHldRoomId();
					if (StringUtils.isNotEmpty(roomId)) {
						FtpUploadUtil.uploadFile("/sjk/" + roomId, logFile.getName(),
								ConfigUtil.getContextProperty("cr.ftp.file.path") + "/"
										+ logFile.getName());
					}

				}
			} catch (Exception e) {
				log.error("sjk ftp job error!, catch error:", e);

			}

		}

	}

	@Scheduled(cron = "0 0/3 * * * ?")
	public void attackLogsFtpTrans() {

		log.info("attack log files ftp start!");

		List<JSONObject> scenes = remoteSceneService.getActiveScenes();
		for (JSONObject scene : scenes) {
			String sceneId = scene.getString("id");
			log.info("sceneId:" + sceneId + ",start ftp job!");
			try {
				List<MpLogFile> logFiles = remoteSceneService.getMpSceneFiles(sceneId,
						ConfigUtil.getContextProperty("cr.ftp.attack_logs.names"));

				log.info("log lists:" + logFiles.toString());
				for (MpLogFile logFile : logFiles) {
					String roomId = hldRoomSceneMapper.selectByMpSceneId(sceneId).getHldRoomId();
					if (StringUtils.isNotEmpty(roomId)) {
						FtpUploadUtil.uploadFile("/attack/" + roomId, logFile.getName(),
								ConfigUtil.getContextProperty("cr.ftp.file.path") + "/"
										+ logFile.getName());
					}
				}
			} catch (Exception e) {
				log.error("attack ftp job error!, catch error:", e);

			}

		}

	}
}
