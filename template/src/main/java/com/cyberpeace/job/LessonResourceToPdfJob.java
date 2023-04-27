package com.cyberpeace.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class LessonResourceToPdfJob {

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	private boolean isCovertPdf;

	private String LOCAL_HOST;

	private String wordPath = ConfigUtil.getContextProperty("cr.pdf.word.file.prefix");

	private String pptPath = ConfigUtil.getContextProperty("cr.pdf.ppt.file.prefix");

	@PostConstruct
	public void init() {{
		isCovertPdf = Boolean.parseBoolean(SystemPropUtil.getProp("COVERT_PDF"));
		LOCAL_HOST = SystemPropUtil.getProp("LOCAL_HOST");

	}}

	@Scheduled(fixedRate =  5 * 60 * 1000)
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
			String covertToPdfCmd = String.format("libreoffice --headless --convert-to pdf:writer_pdf_Export %s --outdir %s", handoutUrl, wordPath);
			log.info(String.format("LOCAL_HOST: %s",LOCAL_HOST));
			log.info(String.format("PASSWD: %s",SystemPropUtil.getProp("PASSWD")));
			executeCommand(LOCAL_HOST,"root",SystemPropUtil.getProp("PASSWD"), covertToPdfCmd);
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

	@Scheduled(fixedRate =  5 * 60 * 1000)
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
			log.info(String.format("update current lesson %s", pptLesson.getName()));
			String covertToPdfCmd = String.format("libreoffice --headless --convert-to pdf:writer_pdf_Export %s --outdir %s", handoutUrl, pptPath);
			String originalPath = getFileName(handoutUrl);
			String newPath = pptPath + pptLesson.getResourceId() + "_" + originalPath + ".pdf";
			executeCommand(LOCAL_HOST,"root",SystemPropUtil.getProp("PASSWD"), covertToPdfCmd);
			FileUtil.copyFile(new File(pptPath,originalPath + ".pdf"), new File(newPath));
			pptLesson.setHandoutUrl(newPath);
			pptLesson.setHandoutType(CourseLesson.HandoutType.PDF.getCode());
			courseLessonMapper.updateById(pptLesson);
			log.info(String.format("update current lesson %s success!!", pptLesson.getName()));
		});

	}

	public static void executeCommand(String host, String user, String password, String command) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0) {
						break;
					}
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					if (in.available() > 0) {
						continue;
					}
					log.info("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(String.format("fail to exec covert cmd case by %s", e.getMessage()));
		}
	}
}