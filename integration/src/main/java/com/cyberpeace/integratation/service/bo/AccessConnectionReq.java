package com.cyberpeace.integratation.service.bo;

import lombok.Data;

@Data
public class AccessConnectionReq {

	private String access_mode_id;

    private Context context;

	private String id;

	private String node_id;
}

@Data
class Context{
    private String access_user_id;
}
