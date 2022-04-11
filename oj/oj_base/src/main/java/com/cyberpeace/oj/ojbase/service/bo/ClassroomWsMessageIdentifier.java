package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

@Data
public class ClassroomWsMessageIdentifier extends WsMessageIdentifier {

	private String classroomId;

}
