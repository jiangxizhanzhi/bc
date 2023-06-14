package com.cyberpeace.cr.crbase.service.context;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.mp.AttackTypeCount;
import com.cyberpeace.cr.crbase.service.context.mp.SceneTrend;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.crbase.service.context.score.GetScoreRsp;
import com.cyberpeace.cr.crbase.service.context.score.ScoreTrendReq;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreTrend;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;

import java.time.Year;
import java.util.*;
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
    List<GetScoreRsp>  redGetScoreRsps;
    @ApiModelProperty("蓝方人员得分")
    List<GetScoreRsp>  blueGetScoreRsps;

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

    private List<String> timeStringList = new ArrayList<>();

    @ApiModelProperty("攻击流量各类别统计")
    private List<AttackTypeCount> attackTypeCount;
    @ApiModelProperty("场景流量趋势图")
    private List<SceneTrend> sceneTrend;

    public void initScoreTrends(List<StudentScoreTrend> scoreTrends) {
        teamTrends = scoreTrends.stream().filter(a-> CrRole.TeamRoleGroup.STUDENT.getCode().equals(a.getRole())).collect(Collectors.toList());
        redTrends = scoreTrends.stream().filter(a-> CrRole.RbRoleGroup.RED.getCode().equals(a.getRole())).collect(Collectors.toList());
        blueTrends = scoreTrends.stream().filter(a-> CrRole.RbRoleGroup.BLUE.getCode().equals(a.getRole())).collect(Collectors.toList());
    }

    public void initTimeList(ScoreTrendReq scoreTrendReq) {
        long start = scoreTrendReq.getStartTime().getTime();
        long end = scoreTrendReq.getEndTime().getTime();
        //需求改回  永远输出年月日格式
//        boolean isSameYear = DateUtil.isSameYear(scoreTrendReq.getStartTime(), scoreTrendReq.getEndTime());
        boolean isSameYear = false;
        //间隔
        long interval = (end-start)/scoreTrendReq.getScoreCount();
        timeList.add(scoreTrendReq.getStartTime());
        if (isSameYear) {
            timeStringList.add(DateUtil.DateToString(scoreTrendReq.getStartTime(),"MM-dd HH:mm:ss"));
        } else {
            timeStringList.add(DateUtil.DateToString(scoreTrendReq.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
        }
        for (int i = 0; i < scoreTrendReq.getScoreCount()-1; i++) {
            Date date = new Date(start + interval * (i + 1));
            timeList.add(date);
            if (isSameYear) {
                timeStringList.add(DateUtil.DateToString(date,"MM-dd HH:mm:ss"));
            } else {
                timeStringList.add(DateUtil.DateToString(date,"yyyy-MM-dd HH:mm:ss"));
            }
        }
        timeList.add(scoreTrendReq.getEndTime());
        if (isSameYear) {
            timeStringList.add(DateUtil.DateToString(scoreTrendReq.getEndTime(),"MM-dd HH:mm:ss"));
        } else {
            timeStringList.add(DateUtil.DateToString(scoreTrendReq.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
        }

    }

    public void initRbGetScoreRsps(Boolean isRb) {
        this.redGetScoreRsps = this.getScoreRsps.stream().filter(a->CrRole.RbRoleGroup.RED.getCode().equals(a.getType())).collect(Collectors.toList());
        this.blueGetScoreRsps = this.getScoreRsps.stream().filter(a->CrRole.RbRoleGroup.BLUE.getCode().equals(a.getType())).collect(Collectors.toList());
        AtomicReference<Integer> index = new AtomicReference<>(1);
        if (isRb) {
            redGetScoreRsps.forEach(a->a.setIndex(index.getAndSet(index.get() + 1)));
            index.set(1);
            blueGetScoreRsps.forEach(a->a.setIndex(index.getAndSet(index.get() + 1)));
        } else {
            getScoreRsps.forEach(a->a.setIndex(index.getAndSet(index.get() + 1)));
        }

    }

    public void initMpData(JSONArray attackTypeCount, JSONArray sceneTrend) {
        this.attackTypeCount = BeanUtils.deepCopyList(attackTypeCount,AttackTypeCount.class);
        this.sceneTrend = BeanUtils.deepCopyList(sceneTrend,SceneTrend.class);


    }
}
