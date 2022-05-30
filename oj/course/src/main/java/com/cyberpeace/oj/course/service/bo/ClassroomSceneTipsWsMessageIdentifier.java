package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;

@Data
public class ClassroomSceneTipsWsMessageIdentifier extends WsMessageIdentifier {

	private String roomId;

}
