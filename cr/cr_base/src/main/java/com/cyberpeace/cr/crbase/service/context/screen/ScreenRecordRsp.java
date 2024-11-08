package com.cyberpeace.cr.crbase.service.context.screen;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 屏幕记录响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class ScreenRecordRsp {

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 用户名称
     */
    private String user_name;

    /**
     * 创建时间
     */
    private String create_time;

    /**
     * recortime
     */
    private BigDecimal recortime;

    /**
     * 结束时间
     */
    private String end_time;

    /**
     * 群
     */
    private Integer group;

    /**
     * 播放 URL
     */
    private String play_url;

    /**
     * 用户头像
     */
    private String user_avatar;

    /**
     * 主机
     */
    private String host;

    /**
     * 文件名称
     */
    private String file_name;
}
