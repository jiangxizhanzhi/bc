package com.cyberpeace.oj.course.service;


public interface CourseDumpService {

	/**
	 * 课程数据资管格式导出
	 * @param resourceId 资源id
	 * @param isDumpTopo 是否下载topo数据
	 * @return file
	 */
	String rMCourseDump(String resourceId, Boolean isDumpTopo, String sceneNumber, Boolean disableImageExport);

}
