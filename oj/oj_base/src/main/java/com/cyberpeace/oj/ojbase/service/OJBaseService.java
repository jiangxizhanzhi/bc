package com.cyberpeace.oj.ojbase.service;


import java.io.IOException;

public interface OJBaseService {

	/**
	 *
	 * @param resource_id 资源id
	 * @param is_dump_topo 是否下载拓扑
	 * @return 文件路径
	 */
	String rMCourseDump( String resource_id, Boolean is_dump_topo) throws IOException;

}
