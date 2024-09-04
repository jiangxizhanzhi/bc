package com.cyberpeace.oj.course.service;

import java.util.List;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;

public interface SharedRepositoryService {

	ListRsp<AdminLessonListBo> getSharedRepository(PageReqBase req, String user);

	ListRsp<AdminLessonListBo> getMyRepository(PageReqBase req);

	void add2My(String resourceId);

	void add2My(List<CourseLesson> lessons);

	void share(List<String> ids);

	void privatize(List<String> ids);

	void delete(List<String> ids);

	List<AdminLessonListBo> getAllMy();

	void saveArrangement(List<String> ids);
}
