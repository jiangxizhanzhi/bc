package com.cyberpeace.common.util;

import java.io.File;

import lombok.extern.slf4j.Slf4j;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

/**
 * @author SN
 * @date 2024/04/16
 */
@Slf4j
public class Mp4Util {

	public static long readDuration(String path) {
		File file = new File(path);
		long duration = 0;
		if (!file.exists() || !file.canRead()) {
			log.error("video file not exist or not readable: [{}]", path);
			return 0;
		}
		try {
			MultimediaObject object = new MultimediaObject(file);
			MultimediaInfo info = object.getInfo();
			duration = info.getDuration();
			return duration;
		} catch (Exception e) {
			log.error("failed to read video duration: {}", path);
			return 0;
		}
	}
}
