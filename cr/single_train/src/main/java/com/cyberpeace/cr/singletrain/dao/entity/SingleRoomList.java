package com.cyberpeace.cr.singletrain.dao.entity;

import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;

import lombok.Data;

/**
 * 单人房名单
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class SingleRoomList {

    /**
     * 业务模板
     *
     * @see BusinessTemplate
     */
    private BusinessTemplate businessTemplate;

    /**
     * 单个模板
     *
     * @see SingleTemplate
     */
    private SingleTemplate singleTemplate;

    /**
     * 单人房
     *
     * @see SingleRoom
     */
    private SingleRoom singleRoom;

    /**
     * 任务计划
     *
     * @see String
     */
    private String missionsScheme;

}
