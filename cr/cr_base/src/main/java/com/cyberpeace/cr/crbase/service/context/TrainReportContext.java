package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.score.GetScoreRsp;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreTrend;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class TrainReportContext {

    @ApiModelProperty("红方得分趋势")
    List<StudentScoreTrend> redTrends;

    @ApiModelProperty("蓝方得分趋势")
    List<StudentScoreTrend> blueTrends;

    @ApiModelProperty("团队得分趋势")
    List<StudentScoreTrend> teamTrends;

    @ApiModelProperty("人员得分")
    List<GetScoreRsp>  getScoreRsps;

    @ApiModelProperty("任务分类个数")
    private Integer taskKindCount;

    @ApiModelProperty("任务分类：key 名称 value个数")
    private Map<String,Integer>  kindMap;

    public void initScoreTrends(List<StudentScoreTrend> scoreTrends) {
        teamTrends = scoreTrends.stream().filter(a-> CrRole.TeamRoleGroup.STUDENT.getCode().equals(a.getRole())).collect(Collectors.toList());
        redTrends = scoreTrends.stream().filter(a-> CrRole.RbRoleGroup.RED.getCode().equals(a.getRole())).collect(Collectors.toList());
        blueTrends = scoreTrends.stream().filter(a-> CrRole.RbRoleGroup.BLUE.getCode().equals(a.getRole())).collect(Collectors.toList());
    }
}
