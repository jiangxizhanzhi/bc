package com.cyberpeace;

import com.cyberpeace.oj.course.service.CourseLessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OJStartupRunner implements CommandLineRunner {

    @Autowired
    private CourseLessonService courseLessonService;
    @Override
    public void run(String... args) throws Exception {
        log.info("OJStartupRunner run");
        courseLessonService.countVideo();
        log.info("OJStartupRunner run end");
    }
}
