package com.cyberpeace.cr.evaluation.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "训练评估")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/train_eval")
@Slf4j
public class TrainEvalController {


}
