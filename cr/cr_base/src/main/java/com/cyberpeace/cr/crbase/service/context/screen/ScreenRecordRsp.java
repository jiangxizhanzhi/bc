package com.cyberpeace.cr.crbase.service.context.screen;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 屏幕记录响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class ScreenRecordRsp {

    /**
     * 用户id
     *
     * @see String
     */
    private String user_id;

    /**
     * 用户名称
     *
     * @see String
     */
    private String user_name;

    /**
     * 创建时间
     *
     * @see String
     */
    private String create_time;

    /**
     * recortime
     *
     * @see BigDecimal
     */
    private BigDecimal recortime;

    /**
     * 结束时间
     *
     * @see String
     */
    private String end_time;

    /**
     * 群
     *
     * @see Integer
     */
    private Integer group;

    /**
     * 播放 URL
     *
     * @see String
     */
    private String play_url;

    /**
     * 用户头像
     *
     * @see String
     */
    private String user_avatar;

    /**
     * 主机
     *
     * @see String
     */
    private String host;

    /**
     * 文件名称
     *
     * @see String
     */
    private String file_name;
}
