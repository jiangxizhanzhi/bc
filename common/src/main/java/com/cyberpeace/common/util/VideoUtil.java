package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.CommonConstant;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author SN
 * @date 2024/04/16
 */
@Slf4j
public class VideoUtil {

	public static Cache<String, Long> videoCache = GuavaCacheUtil.newInstance(CommonConstant.TEN,
			TimeUnit.MINUTES, String.class, Long.class);

	/**
	 * 获取videoduration
	 *
	 * @param path
	 * @return int 时长，单位为秒
	 */
	public static int getVideoDuration(String path) {
		return (int) (readDuration(path) / 1_000_000);
	}

	/**
	 * 获取视频时长，单位为微秒
	 *
	 * @param path 源视频文件
	 * @return 时长（微秒）
	 */
	public static long readDuration(String path) {
		Long duration = videoCache.getIfPresent(path);
		if (null != duration) {
			return duration;
		}

		File file = new File(path);


		if (!file.exists() || !file.canRead()) {
			log.error("video file not exist or not readable: [{}]", path);
			return 0;
		}
		try(FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file)) {
			ff.start();
			duration = ff.getLengthInTime();
			log.info("FFmpegFrameGrabber getLengthInTime is [{}] ", duration);
			ff.release();
			videoCache.put(path, duration);
			return duration;
		} catch (Exception e) {
			log.error("failed to read video duration: {}", path, e);
			return 0;
		}
	}

	/**
	 * 获取帧截图
	 *
	 * @param video 视频文件
	 * @param sec 截图帧时间
	 */
	public static File getFramePic(File video, Integer sec, File capture, Boolean resize) {
		try(FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video)) {
			ff.start();
			long targetTime = (long) sec * 1_000_000;
			ff.setTimestamp(targetTime);
			Frame f = ff.grabFrame();
			if (null != f) {
				Java2DFrameConverter converter = new Java2DFrameConverter();
				BufferedImage srcImage = converter.getBufferedImage(f);
				if (Boolean.TRUE.equals(resize)) {
					// 对截图进行等比例缩放(缩略图)
					int srcImageWidth = srcImage.getWidth();
					int srcImageHeight = srcImage.getHeight();
					int width = 640;
					int height = (int) (((double) width / srcImageWidth) * srcImageHeight);
					BufferedImage thumbnailImage = new BufferedImage(width, height,
							BufferedImage.TYPE_3BYTE_BGR);
					thumbnailImage.getGraphics().drawImage(
							srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0,
							null);

					ImageIO.write(thumbnailImage, "jpg", capture);
				} else {
					ImageIO.write(srcImage, "jpg", capture);
				}
			}
			ff.stop();
		} catch (Exception e) {
			log.error("failed to capture image of {}", video.getAbsolutePath(), e);
		}
		return capture;
	}
}
