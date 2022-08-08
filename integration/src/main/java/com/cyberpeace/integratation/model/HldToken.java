package com.cyberpeace.integratation.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class HldToken implements Serializable {

    private String session_id;

    private String user_id;
}
