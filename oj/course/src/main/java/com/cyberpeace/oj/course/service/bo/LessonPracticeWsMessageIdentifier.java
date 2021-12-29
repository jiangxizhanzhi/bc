package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonPracticeWsMessageIdentifier extends WsMessageIdentifier {

	private String lessonId;

	private String practiceId;

	private Boolean fromWeb;

	private Integer shared;

}
