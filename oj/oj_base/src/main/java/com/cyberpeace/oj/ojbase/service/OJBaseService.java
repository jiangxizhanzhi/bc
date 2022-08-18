package com.cyberpeace.oj.ojbase.service;

import com.cyberpeace.common.model.AuthContext;

import java.io.File;
import java.util.Map;

public interface OJBaseService {

	/**
	 * @param path
	 * @param envReqs
	 * @param authContext
	 * @return
	 */
	File rMCourseDump(String path, Map<String, String> envReqs, AuthContext authContext);

}
