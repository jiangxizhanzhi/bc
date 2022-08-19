package com.cyberpeace.oj.ojbase.service;

import com.cyberpeace.common.model.AuthContext;

import java.io.File;

public interface OJBaseService {

	/**
	 * @param path
	 * @param authContext
	 * @param resource_id
	 * @return
	 */
	File rMCourseDump(String path, AuthContext authContext, String resource_id);

}
