package com.cyberpeace.frame.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.frame.controller.res.StatisticsRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "框架统计接口")
@RestController
@RequestMapping("/frame/statistics")
public class FrameStatisticsController {

    @ApiOperation("统计")
    @GetMapping("")
    public BaseRsp<List<StatisticsRes>> imageUpload() {
        List<StatisticsRes> result = new ArrayList<>();
        StatisticsRes target = new StatisticsRes();
        target.setName("面向攻击目标");
        target.setCount(3);
        target.setThisAdd(1);
        target.setLastAdd(2);
        result.add(target);

        StatisticsRes progress = new StatisticsRes();
        progress.setName("面向攻击流程");
        progress.setCount(4);
        progress.setThisAdd(2);
        progress.setLastAdd(2);
        result.add(progress);

        StatisticsRes total = new StatisticsRes();
        total.setName("总计");
        total.setCount(7);
        total.setThisAdd(3);
        total.setLastAdd(4);

        result.add(total);
        return new BaseRsp<>(result);
    }
}
