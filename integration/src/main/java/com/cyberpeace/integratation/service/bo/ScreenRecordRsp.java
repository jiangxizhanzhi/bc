package com.cyberpeace.integratation.service.bo;

import java.math.BigDecimal;

import lombok.Data;

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

	private String file;

    private String file_name;

	private String file_size;

	private String node_id;

	private String scene;

	private String scene_name;

	private String user_org_name;
}
