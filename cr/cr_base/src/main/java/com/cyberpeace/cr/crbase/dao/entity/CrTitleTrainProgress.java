package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("cr_title_train_progress")
public class CrTitleTrainProgress extends BaseTable {
    private String businessTemplateId;

    private String roomId;

    private String userId;

    private Integer score;

    private String skillPointsScore;
}
