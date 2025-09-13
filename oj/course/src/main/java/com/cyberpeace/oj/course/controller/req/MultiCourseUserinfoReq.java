package com.cyberpeace.oj.course.controller.req;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MultiCourseUserinfoReq {
    private LocalDateTime from;

    private LocalDateTime to;
}
