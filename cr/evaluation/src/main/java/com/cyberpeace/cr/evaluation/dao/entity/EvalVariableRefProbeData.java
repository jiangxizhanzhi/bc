package com.cyberpeace.cr.evaluation.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * 测评变量裁判探针数据
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("cr_eval_variable_ref_probe_data")
public class EvalVariableRefProbeData extends BaseTable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 变量id
     */
    private String variableId;

    /**
     * 探针数据id
     */
    private Integer probeDataId;

    /**
     * 时间
     */
    private Date time;

    /**
     * 格式 化值
     */
    private String formattedValue;

    /**
     * 状态
     */
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
