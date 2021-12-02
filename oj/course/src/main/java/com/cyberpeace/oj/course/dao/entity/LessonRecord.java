package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_lesson_record")
public class LessonRecord extends BaseTable {

	private String lessonId;

	private String userId;

	private String courseId;
}
