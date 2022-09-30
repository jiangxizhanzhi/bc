package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.practice.service.bo.PracticeOperationDumpLoadBo;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryDumpLoadBo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LessonDumpLoadBo implements Serializable {

	private String name;

	private Integer type;

	private Integer handoutType;

	private String handoutUrl;

	private String videoUrl;

	private Integer difficulty;

	private Boolean isPublic;

	private List<String> filePathList;

	private Integer compulsory;

	private Integer destroyDelay;

	private Integer duration;

	private JSONObject topoConfig;

	private String knowledge;

	private Integer sceneShareMode;

	private String originMarkdownFile;

	private String createUserId;

	private Boolean paperPublic;

	private List<PracticeTheoryDumpLoadBo> theoryPractice;

	private List<PracticeOperationDumpLoadBo> operationPractice;
}
