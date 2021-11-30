package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("cr_train_record")
public class TrainRecord implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer businessType;

    private String businessTemplateId;

    private String userId;

    private Date startTime;

    private Date endTime;

}
