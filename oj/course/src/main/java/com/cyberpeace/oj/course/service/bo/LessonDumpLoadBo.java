package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.practice.service.bo.PracticeOperationDumpLoadBo;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryDumpLoadBo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 课程 dump load bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class LessonDumpLoadBo implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;

	/**
	 * 讲义类型
	 *
	 * @see Integer
	 */
	private Integer handoutType;

	/**
	 * 讲义 URL
	 *
	 * @see String
	 */
	private String handoutUrl;

	/**
	 * 视频 URL
	 *
	 * @see String
	 */
	private String videoUrl;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	private Boolean isPublic;

	/**
	 * 文件路径列表
	 *
	 * @see List<String>
	 */
	private List<String> filePathList;

	/**
	 * 必修
	 *
	 * @see Integer
	 */
	private Integer compulsory;

	/**
	 * 销毁延迟
	 *
	 * @see Integer
	 */
	private Integer destroyDelay;

	/**
	 * 期间
	 *
	 * @see Integer
	 */
	private Integer duration;

	/**
	 * 拓扑配置
	 *
	 * @see JSONObject
	 */
	private JSONObject topoConfig;

	/**
	 * 知识
	 *
	 * @see String
	 */
	private String knowledge;

	/**
	 * 场景共享模式
	 *
	 * @see Integer
	 */
	private Integer sceneShareMode;

	/**
	 * origin markdown 文件
	 *
	 * @see String
	 */
	private String originMarkdownFile;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 论文 公共
	 *
	 * @see Boolean
	 */
	private Boolean paperPublic;

	/**
	 * 理论实践
	 *
	 * @see List<PracticeTheoryDumpLoadBo>
	 */
	private List<PracticeTheoryDumpLoadBo> theoryPractice;

	/**
	 * 实操实践
	 *
	 * @see List<PracticeOperationDumpLoadBo>
	 */
	private List<PracticeOperationDumpLoadBo> operationPractice;
}
