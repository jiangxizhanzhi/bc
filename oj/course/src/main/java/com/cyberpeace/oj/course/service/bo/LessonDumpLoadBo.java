package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.practice.service.bo.PracticeOperationDumpLoadBo;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryDumpLoadBo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 课程 dump load bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class LessonDumpLoadBo implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 讲义类型
	 */
	private Integer handoutType;

	/**
	 * 讲义 URL
	 */
	private String handoutUrl;

	/**
	 * 视频 URL
	 */
	private String videoUrl;

	/**
	 * 难度
	 */
	private Integer difficulty;

	/**
	 * 是否公开
	 */
	private Boolean isPublic;

	/**
	 * 文件路径列表
	 */
	private List<String> filePathList;

	/**
	 * 必修
	 */
	private Integer compulsory;

	/**
	 * 销毁延迟
	 */
	private Integer destroyDelay;

	/**
	 * 期间
	 */
	private Integer duration;

	/**
	 * 拓扑配置
	 */
	private JSONObject topoConfig;

	/**
	 * 知识
	 */
	private String knowledge;

	/**
	 * 场景共享模式
	 */
	private Integer sceneShareMode;

	/**
	 * origin markdown 文件
	 */
	private String originMarkdownFile;

	/**
	 * 创造用户id
	 */
	private String createUserId;

	/**
	 * 论文 公共
	 */
	private Boolean paperPublic;

	/**
	 * 理论练习
	 */
	private List<PracticeTheoryDumpLoadBo> theoryPractice;

	/**
	 * 实操练习
	 */
	private List<PracticeOperationDumpLoadBo> operationPractice;
}
