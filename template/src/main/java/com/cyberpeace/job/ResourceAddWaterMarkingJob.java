package com.cyberpeace.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.*;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.cyberpeace.common.util.CryptoUtils.DEFAULT_SECRET_KEY2;

@Slf4j
@Component
public class ResourceAddWaterMarkingJob {


    @Autowired
    private CourseLessonMapper courseLessonMapper;

    // TODO 时间间隔恢复
    @Scheduled(cron="0 0/5 * * * ?")
    public void videoAdd() {
        if(!Boolean.parseBoolean(ConfigUtil.getContextProperty("ojj.isCrypto"))){
            return;
        }
        String path = "/cpms/crypto";
        FileUtil.mkdirs(path);
        List<String> attachmentVideoList = FileUtil.listDirs(
                new File(ResourceImportConstant.RESOURCE_CRYPTO_PATH).getAbsolutePath(), true);
        List<String> cryptoVideo = getCryptoVideoList(attachmentVideoList);
        LambdaQueryWrapper<CourseLesson> filterQueryVideo = new LambdaQueryWrapper<>();
        if(cryptoVideo.size() > 0){
            filterQueryVideo.notIn(CourseLesson::getResourceId,cryptoVideo);
        }
        filterQueryVideo.isNotNull(CourseLesson::getVideoUrl);
        List<CourseLesson> unMarked = courseLessonMapper.selectList(filterQueryVideo);
        unMarked.forEach(courseLesson ->{
            String video = courseLesson.getVideoUrl();
            if(StringUtils.isBlank(video)){
                return;
            }
            if(!video.startsWith("/cpms")){
                video = ConfigUtil.getContextProperty("cr.local.file.prefix") + video;
            }
//            video = "D:" + video;
            HashMap<String, String> dto= new HashMap<>();
            dto.put("ffmpeg_path", "ffmpeg");//必填
            dto.put("input_path", video);//必填
            String[] split = video.split("/");
            if(split.length < 1){
                return;
            }
            String videoPath = split[split.length - 1];
            String[] split1 = videoPath.split("\\.");
            if(split1.length < 1){
                return;
            }
            String videoName = split1[0];
            FileUtil.copyFile(new File(video), new File(path + File.separator + courseLesson.getResourceId() + "_" + videoPath));
            String replaceVideo = video.replace(videoName, CryptoUtils.encode64(DEFAULT_SECRET_KEY2, videoName));
            dto.put("video_converted_path", replaceVideo);//必填
            log.info(String.format("start to add %s water marking!!", video));
            String secondsString=  new FFMPEG().videoTransfer(dto);
            log.info("转换共用:"+secondsString+"秒");
            courseLesson.setVideoUrl(replaceVideo);
            courseLessonMapper.updateById(courseLesson);
        });
//        ShellUtils.execShell("rm -rf /cpms/waterImage*");
    }


    private List<String> getCryptoList(List<String> attachmentVideoList) {
        List<String> cryptoVideo = new ArrayList<>();
        attachmentVideoList.forEach(attachment->{
            String[] split = attachment.split("_");
            if(split.length >= 2 && !(attachment.contains(".mp4") || attachment.contains(".m4v"))){
                String[] split1 = split[0].split("/");
                if(split1.length > 2 && !cryptoVideo.contains(split1[split1.length - 1])){
                    cryptoVideo.add(split1[split1.length - 1]);
                }
            }
        });
        return cryptoVideo;
    }

    private List<String> getCryptoVideoList(List<String> attachmentVideoList) {
        List<String> cryptoVideo = new ArrayList<>();
        attachmentVideoList.forEach(attachment->{
            String[] split = attachment.split("_");
            if(split.length >= 2 && (attachment.contains(".mp4") || attachment.contains(".m4v"))){
                String[] split1 = split[0].split("/");
                if(split1.length > 2 && !cryptoVideo.contains(split1[split1.length - 1])){
                    cryptoVideo.add(split1[split1.length - 1]);
                }
            }
        });
        return cryptoVideo;
    }
    // TODO 时间间隔恢复
    @Scheduled(cron="0 0/5 * * * ?")
    public void officeResourceAdd() {
        if(!Boolean.parseBoolean(ConfigUtil.getContextProperty("ojj.isCrypto"))){
            return;
        }
        String path = "/cpms/crypto";
        FileUtil.mkdirs(path);
        List<String> attachmentList = FileUtil.listDirs(
                new File(ResourceImportConstant.RESOURCE_CRYPTO_PATH).getAbsolutePath(), true);
        List<String> crypto = getCryptoList(attachmentList);
        LambdaQueryWrapper<CourseLesson> filterQuery = new LambdaQueryWrapper<>();
        if(crypto.size() > 0){
            filterQuery.notIn(CourseLesson::getResourceId,crypto);
        }
        filterQuery.isNotNull(CourseLesson::getHandoutUrl);
        filterQuery.ne(CourseLesson::getHandoutType, CourseLesson.HandoutType.MD.getCode());
        List<CourseLesson> courseLessons = courseLessonMapper.selectList(filterQuery);
        String markStr = "南京赛宁";
        courseLessons.forEach(
                courseLesson -> {
                    String handout = courseLesson.getHandoutUrl();
                    if(StringUtils.isBlank(handout)){
                        return;
                    }
                    Integer handoutType = courseLesson.getHandoutType();
                    String[] split = handout.split("/");
                    if(split.length < 1){
                        return;
                    }
                    if(!handout.startsWith("/cpms")){
                        handout = ConfigUtil.getContextProperty("cr.local.file.prefix") + handout;
                    }
                    String handoutPath = split[split.length - 1];
//                    handout = "D:" + handout;
                    String[] split1 = handoutPath.split("\\.");
                    if(split1.length < 1){
                        return;
                    }
                    String handoutName = split1[0];
                    String replacehandout = handout.replace(handoutName, CryptoUtils.encode64(DEFAULT_SECRET_KEY2, courseLesson.getResourceId()));
                    log.info(String.format("replacehandout is %s", replacehandout));
                    File file = new File(handout);
                    File destFile = new File(path + File.separator + courseLesson.getResourceId() + "_" + handoutPath);
                    if(!file.exists() || destFile.exists()){
                        return;
                    }
                    FileUtil.copyFile(file, destFile);
                    // TODO
                    log.info(String.format("start to add %s water marking!!", handout));
                    String inputPath = handout.split("\\.")[0].replace(handoutName, "");
                    try {
                        if (handoutType.equals(CourseLesson.HandoutType.PPT.getCode())) {
                            log.info(String.format("inputpath is %s", inputPath));
                            log.info(String.format("handoutName is %s", handoutName));
//                            WaterMarkingUtils.addPPTWatermark(handout, markStr, null, replacehandout);
                            WaterMarkingUtils.setPPTWaterMark(handout, replacehandout, markStr);
                        } else if (handoutType.equals(CourseLesson.HandoutType.DOC.getCode())) {
//                            WaterMarkingUtils.addWordWaterMark(handout, markStr,null,inputPath, replacehandout);
                            WaterMarkingUtils.addWordWaterMark1(handout, markStr,null ,replacehandout);

                        } else if (handoutType.equals(CourseLesson.HandoutType.PDF.getCode())) {
                            WaterMarkingUtils.setPdfWatermark(handout, replacehandout, markStr);
                        } else if (handoutType.equals(CourseLesson.HandoutType.XLS.getCode())) {
                            WaterMarkingUtils.setExcelWaterMark(handout, replacehandout, markStr);
                        }
                        courseLesson.setHandoutUrl(replacehandout);
                        courseLessonMapper.updateById(courseLesson);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new BusinessException("water marking failed!!");

                    }
                }
        );
//        ShellUtils.execShell("rm -rf /cpms/waterImage*");
    }
}
