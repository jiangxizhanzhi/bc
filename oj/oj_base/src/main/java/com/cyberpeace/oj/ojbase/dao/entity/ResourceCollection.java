package com.cyberpeace.oj.ojbase.dao.entity;

import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
public class ResourceCollection extends BaseTable {

    private String userId;

    private String businessId;

    private Integer businessType;

}
