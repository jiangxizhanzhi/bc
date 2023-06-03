package com.cyberpeace.cr.crbase.service.context.screen;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ScreenRecordRsp {

    private String user_id;

    private String user_name;

    private String create_time;

    private BigDecimal recortime;

    private String end_time;

    private Integer group;

    private String play_url;

    private String user_avatar;

    private String host;

    private String file_name;
}
