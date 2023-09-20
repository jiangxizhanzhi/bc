package com.cyberpeace.oj.course.service;

import com.coremedia.iso.IsoFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Mp4Util {

	public static String getVideoTimeOfLocalFile(String videoPath) {
		if (!videoPath.startsWith("/cpms")) {
			log.info("invalid video path [{}]", videoPath);
			return "";
		}
		log.info("try to get video length of [{}]", videoPath);
		long playTime = 0L;
		try (IsoFile file = new IsoFile(videoPath);) {
			playTime = file.getMovieBox().getMovieHeaderBox().getDuration()
					/ file.getMovieBox().getMovieHeaderBox().getTimescale();
		} catch (Exception e) {
			log.error("failed to get video length of [{}]", videoPath, e);
		}
		if (playTime == 0L) {
			return "";
		}
		long sec = playTime;
		long min = sec / 60;
		sec = sec % 60;
		long hour = min / 60;
		min = min % 60;

		String res = String.format("%02d:%02d:%02d", hour, min, sec);
		return res;
	}

}
