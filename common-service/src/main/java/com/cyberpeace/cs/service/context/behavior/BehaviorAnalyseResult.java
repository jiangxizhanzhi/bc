package com.cyberpeace.cs.service.context.behavior;

import lombok.Data;

@Data
public class BehaviorAnalyseResult {

	private String mostPopularCourse;

	private String mostPopularLesson;

	private PracticeResult practice;

	private PracticeResult theory;
}
