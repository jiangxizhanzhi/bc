package com.cyberpeace.cr.crbase.service.context.score;

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

    public ScoreTrendReq(Date startTime, Date endTime, Integer scoreCount, String replaySceneId) {
        this.startTime = startTime;
        this.endTime = endTime == null? new Date(): endTime;
        this.scoreCount = scoreCount;
        this.replaySceneId = replaySceneId;
    }
}
