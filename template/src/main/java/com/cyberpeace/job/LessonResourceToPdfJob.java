package com.cyberpeace.job;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class LessonResourceToPdfJob {

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	private boolean isCovertPdf;

	private String LIBREOFFICE_URL;

	private String wordPath = ConfigUtil.getContextProperty("cr.pdf.word.file.prefix");

	private String pptPath = ConfigUtil.getContextProperty("cr.pdf.ppt.file.prefix");

	@PostConstruct
	public void init() {{
		isCovertPdf = Boolean.parseBoolean(SystemPropUtil.getProp("COVERT_PDF"));
		LIBREOFFICE_URL = "http://" + SystemPropUtil.getProp("PY_HOST")  + ":" + SystemPropUtil.getProp("PY_PORT") + "/covert_to_pdf/";

	}}

	@Scheduled(fixedRate =  1 * 60 * 1000)
	public void covertWordToPdf() {
		log.info("query word lesson resources start----------");
		if(!isCovertPdf){
			return;
		}
		List<String> attachmentList = FileUtil.listDirs(
				new File(wordPath).getAbsolutePath(), true);
		List<String> wordLists = getCovertToPdfList(attachmentList);
		LambdaQueryWrapper<CourseLesson> filterWordQuery = new LambdaQueryWrapper<>();
		filterWordQuery.eq(CourseLesson::getHandoutType, CourseLesson.HandoutType.DOC.getCode());
		filterWordQuery.isNotNull(CourseLesson::getHandoutUrl);
		if(!wordLists.isEmpty()){
			filterWordQuery.notIn(CourseLesson::getResourceId, wordLists);
		}
		List<CourseLesson> wordLessons = courseLessonMapper.selectList(filterWordQuery);
		FileUtil.createPath(new File(ConfigUtil.getContextProperty("cr.pdf.word.file.prefix")));
		log.info("query finish start to covert word to pdf");
		wordLessons.forEach(wordLesson -> {
			String handoutUrl = wordLesson.getHandoutUrl();
			if(!handoutUrl.startsWith("/cpms")){
				handoutUrl = "/cpms/" + handoutUrl;
			}
			String covertToPdfCmd = String.format("cd /cpms/pdf/word/ && libreoffice --headless --convert-to pdf:writer_pdf_Export %s --outdir %s", handoutUrl, wordPath);
			log.info(String.format("current cmd is %s", covertToPdfCmd));
			JSONObject body = new JSONObject();
			body.put("data", covertToPdfCmd);
			JSONObject post = HttpClientUtil.post(LIBREOFFICE_URL, body, JSONObject.class);
			String error = (String) post.get("error");
			if("empty".equals(error)){
				log.info(String.format("%s covert to pdf failed  for %s", handoutUrl, error));
				return;
			}
			String originalPath = getFileName(handoutUrl);
			String newPath = wordPath + wordLesson.getResourceId() + "_" + originalPath + ".pdf";
			FileUtil.copyFile(new File(wordPath,originalPath + ".pdf"), new File(newPath));
			wordLesson.setHandoutUrl(newPath);
			log.info(String.format("update current lesson %s", wordLesson.getName()));
			courseLessonMapper.updateById(wordLesson);
			log.info(String.format("update current lesson %s success!!", wordLesson.getName()));
		});
	}

	private List<String> getCovertToPdfList(List<String> attachmentVideoList) {
		List<String> res = new ArrayList<>();
		attachmentVideoList.forEach(attachment->{
			String[] split = attachment.split("_");
			if(split.length >= 2){
				String[] split1 = split[0].split(CommonConstant.LEFT_OBLIQUE_LINE);
				if(split1.length > 2 && !res.contains(split1[split1.length - 1])){
					res.add(split1[split1.length - 1]);
				}
			}
		});
		return res;
	}

	private String getFileName(String handoutUrl) {
		String[] split = handoutUrl.split("/");
		String fileName = split[split.length - 1];
		String[] file = fileName.split("\\.");
		return file[0];
	}

	@Scheduled(fixedRate =  1 * 60 * 1000)
	public void covertPptToPdf() {
		log.info("query ppt lesson resources start----------");
		if(!isCovertPdf){
			return;
		}
		List<String> attachmentList = FileUtil.listDirs(
				new File(pptPath).getAbsolutePath(), true);
		List<String> pptLists = getCovertToPdfList(attachmentList);
		LambdaQueryWrapper<CourseLesson> filterPptQuery = new LambdaQueryWrapper<>();
		filterPptQuery.eq(CourseLesson::getHandoutType, CourseLesson.HandoutType.PPT.getCode());
		filterPptQuery.isNotNull(CourseLesson::getHandoutUrl);
		if(!pptLists.isEmpty()){
			filterPptQuery.notIn(CourseLesson::getResourceId, pptLists);
		}
		List<CourseLesson> pptLessons = courseLessonMapper.selectList(filterPptQuery);
		log.info("query finish start to covert ppt to pdf");
		FileUtil.createPath(new File(ConfigUtil.getContextProperty("cr.pdf.ppt.file.prefix")));
		pptLessons.forEach(pptLesson -> {
			String handoutUrl = pptLesson.getHandoutUrl();
			if(!handoutUrl.startsWith("/cpms")){
				handoutUrl = "/cpms/" + handoutUrl;
			}
			String fileName = getFileName(handoutUrl);
			File pdfFile = new File(pptPath, fileName + "_" + pptLesson.getResourceId() + ".pdf");
			log.info(String.format("update current lesson %s", pptLesson.getName()));
			Boolean aBoolean = FileUtil.convertPPTToPDF(new File(handoutUrl), pdfFile);
			if(aBoolean){
				pptLesson.setHandoutUrl(pdfFile.getAbsolutePath());
				courseLessonMapper.updateById(pptLesson);
			}
			log.info(String.format("update current lesson %s success!!", pptLesson.getName()));
		});

	}
}