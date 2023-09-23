package com.cyberpeace.cr.evaluation.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("cr_eval_variable_ref_probe_data")
public class EvalVariableRefProbeData extends BaseTable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String variableId;

    private Integer probeDataId;

    private Date time;

    private String formattedValue;

    private Integer status;

    public enum Status implements GenericEnum {
        NONE(0, "无"),
        MATCHED(1, "被匹配");

        @Getter
        private final Integer code;
        @Getter
        private final String desc;

        Status(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
