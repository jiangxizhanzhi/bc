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
/**
 * 这段代码定义了一个工具类 `VideoUtil`，用于处理视频相关的操作。以下是代码的主要功能和结构分析： 
 * ### 主要功能 
 * 1. **获取视频时长**： 
 * - 提供了两个方法来获取视频的时长： 
 * - `getVideoDuration(String path)`：返回视频时长（以秒为单位）。 
 * - `readDuration(String path)`：返回视频时长（以微秒为单位），并使用缓存机制避免重复计算。 
 * 2. **获取视频帧截图**： 
 * - `getFramePic(File video, Integer sec, File capture, Boolean resize)`：从指定时间点抓取视频帧，并保存为图片文件。支持图片缩放功能。 
 * ### 关键特性 
 * - **缓存机制**：使用 Guava Cache 缓存视频时长，减少重复计算开销。 
 * - **依赖外部库**：依赖 `org.bytedeco.javacv` 和 `javax.imageio` 库，用于视频处理和图像操作。 
 * - **异常处理**：在视频读取或截图过程中捕获异常并记录日志。 
 * ### 结构说明 
 * - **静态变量**： 
 * - `videoCache`：Guava Cache 实例，用于缓存视频时长。 
 * - **静态方法**： 
 * - `getVideoDuration`：调用 `readDuration` 并将结果转换为秒。 
 * - `readDuration`：读取视频时长，优先从缓存中获取，否则通过 `FFmpegFrameGrabber` 计算。 
 * - `getFramePic`：抓取指定时间点的视频帧并保存为图片，支持缩放。 
 * ### 总结 
 * 该工具类封装了视频时长获取和帧截图的功能，适用于需要处理视频文件的应用场景。通过缓存机制优化了性能，同时提供了灵活的截图选项（如是否缩放）。 
**/
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
        log.info("platform: {}, loadLib: {}", org.bytedeco.javacpp.Loader.getPlatform(), org.bytedeco.javacpp.Loader.getLoadedLibraries());

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

