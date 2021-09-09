package com.cyberpeace.oj.course.controller.admin;

import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "oj-课时后台api")
@RestController
@RequestMapping("/admin/lesson")
@RequiresAuthentication
public class AdminLessonController {
}
