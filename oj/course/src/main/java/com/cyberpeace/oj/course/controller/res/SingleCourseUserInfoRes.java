package com.cyberpeace.oj.course.controller.res;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;

import java.util.Optional;

@Data
@NoArgsConstructor
public class SingleCourseUserInfoRes {
    /**
     * 用户id，不要使用自增数字编号（必须）
     */
    private String userId;

    /**
     * 姓名（文字显示）
     */
    private String name;

    /**
     * 用户名（对前端显示，登录使用）
     */
    private String userName;

    /**
     * 头像地址
     */
    private String avatarUrl;



    private Integer joinedLessonNums;


    private Integer finishedLessonNums;

    private Integer finishedTaskNums;

    private Integer trainTimeSum;

    private Integer averageTrainScore;

    private Integer trainScoreSum;

    private Integer ranking;


    public void addLessonData(String userId, Integer finishedTasks, boolean lessonFinishedFlag, Integer trainHours, Integer score, RemoteUserService remoteUserService) {
        if (StringUtils.isEmpty(this.userId)) {
            UserInfo userInfo = remoteUserService.getById(userId);
            this.userId = userInfo.getUserId();
            this.name = userInfo.getName();
            this.userName = userInfo.getUserName();
            this.avatarUrl = userInfo.getAvatarUrl();
        }

        this.joinedLessonNums = Optional.ofNullable(joinedLessonNums).orElse(0) + 1;

        this.trainTimeSum = Optional.ofNullable(trainTimeSum).orElse(0) + Optional.ofNullable(trainHours).orElse(0);
        int lessonFinished = lessonFinishedFlag ? 1 : 0;
        this.finishedLessonNums = Optional.ofNullable(this.finishedLessonNums).orElse(0) + lessonFinished;
        this.finishedTaskNums = Optional.ofNullable(this.finishedTaskNums).orElse(0) + Optional.ofNullable(finishedTasks).orElse(0);
        this.trainScoreSum = Optional.ofNullable(this.trainScoreSum).orElse(0) + Optional.ofNullable(score).orElse(0);
        this.averageTrainScore = trainScoreSum / finishedLessonNums;
    }
}
