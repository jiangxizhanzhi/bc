package com.cyberpeace.cr.crbase.dao.entity;

import com.cyberpeace.common.entity.CrRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserIdRole {

    private String userId;
    private CrRole role;
}
