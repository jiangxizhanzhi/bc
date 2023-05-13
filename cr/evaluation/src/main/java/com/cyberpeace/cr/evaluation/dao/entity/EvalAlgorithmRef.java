package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@TableName("cr_eval_algorithm_ref")
public class EvalAlgorithmRef extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    private String description;

    private Integer type;

    private Integer algorithmLanguage;

    private String code;

    private List<Integer> matrixData;

    public enum Params implements GenericEnum {
        NUMBER(0, "Number"),
        STRING(1, "String"),
        BOOL(2, "Bool"),
        IP(3, "IP");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Params(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

}
