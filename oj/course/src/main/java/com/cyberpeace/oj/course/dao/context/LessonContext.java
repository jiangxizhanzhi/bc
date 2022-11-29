package com.cyberpeace.oj.course.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;
import lombok.Data;

@Data
public class LessonContext extends PatternBase {

	private Integer type;

	private List<String> courseIds;

}
