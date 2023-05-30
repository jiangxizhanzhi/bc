package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.crbase.service.context.score.GetScoreRsp;
import com.cyberpeace.cr.crbase.service.context.score.ScoreTrendReq;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreTrend;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
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

    @ApiModelProperty("角色信息")
    private List<UserRole>  userRoles;

    @ApiModelProperty("基础信息")
    private RoomBaseInfo roomBaseInfo;

    private String teacherName;
    @ApiModelProperty("管理员")
    private String adminName;
    @ApiModelProperty("导演")
    private String directorName;
    @ApiModelProperty("裁判")
    private String refereesName;
    private List<Date> timeList = new ArrayList<>();

    public void initScoreTrends(List<StudentScoreTrend> scoreTrends) {
        teamTrends = scoreTrends.stream().filter(a-> CrRole.TeamRoleGroup.STUDENT.getCode().equals(a.getRole())).collect(Collectors.toList());
        redTrends = scoreTrends.stream().filter(a-> CrRole.RbRoleGroup.RED.getCode().equals(a.getRole())).collect(Collectors.toList());
        blueTrends = scoreTrends.stream().filter(a-> CrRole.RbRoleGroup.BLUE.getCode().equals(a.getRole())).collect(Collectors.toList());
    }

    public void initTimeList(ScoreTrendReq scoreTrendReq) {
        long start = scoreTrendReq.getStartTime().getTime();
        long end = scoreTrendReq.getEndTime().getTime();
        //间隔
        long interval = (end-start)/scoreTrendReq.getScoreCount();
        timeList.add(scoreTrendReq.getStartTime());
        for (int i = 0; i < scoreTrendReq.getScoreCount()-1; i++) {
            timeList.add(new Date(start+interval*(i+1)));
        }
        timeList.add(scoreTrendReq.getEndTime());


    }
}
