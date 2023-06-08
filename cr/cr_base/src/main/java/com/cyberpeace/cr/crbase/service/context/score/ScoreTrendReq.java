package com.cyberpeace.cr.crbase.service.context.score;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ScoreTrendReq {

    private Date startTime;

    private Date endTime;

    private Integer scoreCount;

    private String replaySceneId;
}
