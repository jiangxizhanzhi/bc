package com.cyberpeace.oj.course.service;

public interface CourseDumpService {

    /**
     * 课程数据资管格式导出
     * @param resource_id 资源id
     * @param is_dump_topo 是否下载topo数据
     * @return file
     */
    String rMCourseDump(String resource_id, Boolean is_dump_topo, String sceneNumber, Boolean disableImageExport);


}
