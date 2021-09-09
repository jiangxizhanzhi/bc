package com.cyberpeace.oj.course.controller.web;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiresAuthentication
@RequestMapping("/web/lesson")
public class WebLessonController {

}
