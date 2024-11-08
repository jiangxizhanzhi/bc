package com.cyberpeace.cr.crbase.service.context.score;

import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 得分趋势请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@NoArgsConstructor
public class ScoreTrendReq {

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 得分计数
     */
    private Integer scoreCount;

    /**
     * 复盘场景id
     */
    private String replaySceneId;

    public ScoreTrendReq(TrainRecord trainRecord, Date roomStartTime, Date roomEndTime, Integer scoreCount, String replaySceneId) {
        if (trainRecord!=null) {
            this.startTime = trainRecord.getStartTime();
            this.endTime = trainRecord.getEndTime() == null? new Date(): trainRecord.getEndTime();
        } else {
            this.startTime = roomStartTime;
            this.endTime = roomEndTime;
        }

        this.scoreCount = scoreCount;
        this.replaySceneId = replaySceneId;
    }
}
