package com.cyberpeace.cr.crbase.service.context.score;

import java.util.Date;

import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScoreTrendReq {

    private Date startTime;

    private Date endTime;

    private Integer scoreCount;

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
