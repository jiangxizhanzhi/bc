package com.cyberpeace.cr.crbase.service.context;

import lombok.Data;

import java.util.List;

@Data
public class Auth {
    private Integer authMode;

    private List<String> authIds;
}
