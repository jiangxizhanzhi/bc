package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

@Data
public class ClassHelpWsMessageIdentifier extends WsMessageIdentifier {

    private String classroomId;

}
