package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class CourseSceneContext implements Serializable {

	public CourseSceneContext(List<OjLocalScene> scenes, List<String> topos) {
		this.scenes = scenes;
		this.topos = topos;
	}

	private List<OjLocalScene> scenes;

	private List<String> topos;

}
