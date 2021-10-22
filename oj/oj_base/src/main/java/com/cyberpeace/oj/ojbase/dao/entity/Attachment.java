package com.cyberpeace.oj.ojbase.dao.entity;

import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
public class Attachment extends BaseTable {

    private String resourceId;

    private String fileName;

    private String filePath;

}
