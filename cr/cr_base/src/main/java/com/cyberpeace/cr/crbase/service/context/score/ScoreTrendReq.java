package com.cyberpeace.cr.crbase.service.context.score;

import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ScoreTrendReq {

    private Date startTime;

    private Date endTime;

    private Integer scoreCount;

    private String replaySceneId;

    public ScoreTrendReq(TrainRecord trainRecord, Date roomStartTime, Integer scoreCount, String replaySceneId) {
        if (trainRecord!=null) {
            this.startTime = trainRecord.getStartTime();
            this.endTime = trainRecord.getEndTime() == null? new Date(): trainRecord.getEndTime();
        } else {
            this.startTime = roomStartTime;
            this.endTime = new Date();
        }

        this.scoreCount = scoreCount;
        this.replaySceneId = replaySceneId;
    }
}
