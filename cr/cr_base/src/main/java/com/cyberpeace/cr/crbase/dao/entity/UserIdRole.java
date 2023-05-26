package com.cyberpeace.cr.crbase.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserIdRole {

    private String userId;
    private Integer role;
}
