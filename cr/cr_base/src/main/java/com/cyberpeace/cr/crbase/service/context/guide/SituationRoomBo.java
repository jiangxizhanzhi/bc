package com.cyberpeace.cr.crbase.service.context.guide;

import lombok.Data;

/**
 * 态势房间Bo
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationRoomBo {

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 训练状态
     */
    private Integer trainingStatus;
}
