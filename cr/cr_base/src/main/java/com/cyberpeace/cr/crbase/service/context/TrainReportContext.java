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
import java.util.concurrent.atomic.AtomicReference;
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
    @ApiModelProperty("红方人员得分")
    List<GetScoreRsp>  RedGetScoreRsps;
    @ApiModelProperty("蓝方人员得分")
    List<GetScoreRsp>  BlueGetScoreRsps;

    @ApiModelProperty("任务分类个数")
    private Integer taskKindCount;

    @ApiModelProperty("任务分类：key 名称 value个数")
    private Map<String,Integer>  kindMap;
    @ApiModelProperty("红方任务分类：key 名称 value个数")
    private Map<String,Integer>  redMap;
    @ApiModelProperty("蓝方任务分类：key 名称 value个数")
    private Map<String,Integer>  blueMap;

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

    public void initRbGetScoreRsps() {
        this.RedGetScoreRsps = this.getScoreRsps.stream().filter(a->CrRole.RbRoleGroup.RED.getCode().equals(a.getType())).collect(Collectors.toList());
        this.BlueGetScoreRsps = this.getScoreRsps.stream().filter(a->CrRole.RbRoleGroup.BLUE.getCode().equals(a.getType())).collect(Collectors.toList());
        AtomicReference<Integer> index = new AtomicReference<>(1);
        getScoreRsps.forEach(a->a.setIndex(index.getAndSet(index.get() + 1)));
        index.set(1);
        RedGetScoreRsps.forEach(a->a.setIndex(index.getAndSet(index.get() + 1)));
        index.set(1);
        BlueGetScoreRsps.forEach(a->a.setIndex(index.getAndSet(index.get() + 1)));
    }
}
