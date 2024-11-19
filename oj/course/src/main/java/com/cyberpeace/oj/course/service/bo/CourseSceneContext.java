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
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
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
	 *
	 * @see List<OjLocalScene>
	 */
	private List<OjLocalScene> scenes;

	/**
	 * 拓扑
	 *
	 * @see List<String>
	 */
	private List<String> topos;

}
