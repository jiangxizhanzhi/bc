package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.ojbase.controller.req.RMImportReq;
import lombok.Data;

import java.util.Map;

@Data
public class RMCourseImportReq extends RMImportReq {

	/** key=课时或习题名称 value=topoId*/
	private Map<String, String> envReqs;

}
