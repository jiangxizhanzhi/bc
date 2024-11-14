package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 课程场景上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@NoArgsConstructor
public class CourseSceneContext implements Serializable {

	public CourseSceneContext(List<OjLocalScene> scenes, List<String> topos) {
		this.scenes = scenes;
		this.topos = topos;
	}

	/**
	 * 场景
	 */
	private List<OjLocalScene> scenes;

	/**
	 * 拓扑
	 */
	private List<String> topos;

}
