package com.cyberpeace.oj.rtks.controller.res;

import com.cyberpeace.oj.rtks.dao.entity.Knowledge;
import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;
import lombok.Data;
import java.util.List;

@Data
public class KnowledgeRes extends Knowledge {

	private String category;

	private List<RelatedCourse> courseList;
}