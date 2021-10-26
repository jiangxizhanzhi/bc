package com.cyberpeace.oj.course.constant;

public interface LessonPathConstant {

    /** 课程目录*/
    String COURSE_PATH = "/bc/rs/oj/course/{0}";

    /** 课时目录*/
    String LESSON_PATH = COURSE_PATH + "/{1}";

    /** 讲义存放地址*/
    String HANDOUT_PATH = LESSON_PATH + "handout";

    /** 视频存放地址*/
    String VIDEO_PATH = LESSON_PATH + "/video";

    /** markdown原文件存放地址*/
    String ORIGIN_MD_PATH = COURSE_PATH + "/markdown";

}
