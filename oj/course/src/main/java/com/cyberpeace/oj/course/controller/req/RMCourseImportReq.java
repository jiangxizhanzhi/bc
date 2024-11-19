package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.ojbase.controller.req.RMImportReq;
import lombok.Data;

import java.util.Map;

/**
 * RMCOURSE 课程导入请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see RMImportReq
 * @since 1.0.0
 */
@Data
public class RMCourseImportReq extends RMImportReq {

	/**
	 * key=课时或习题名称 value=topoId
	 *
	 * @see Map<String, String>
	 */
	private Map<String, String> envReqs;

	/**
	 * key=课时名称 value=<实操题名称，topoId>
	 *
	 * @see Map<String, Map<String, String>>
	 */
	private Map<String, Map<String, String>> operationEnvReqs;
}
