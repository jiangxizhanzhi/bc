package com.cyberpeace.cr.crbase.service.context.screen;

import lombok.Data;

import java.util.Date;

@Data
public class ScreenRecordRsp {

    private String user_id;

    private String user_name;

    private Date create_time;

    private Long recortime;

    private Date end_time;

    private Integer group;

}
