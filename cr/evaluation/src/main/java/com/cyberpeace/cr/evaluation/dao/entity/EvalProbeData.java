package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("cr_eval_probe_data")
public class EvalProbeData extends BaseTable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String sceneId;

    private String deviceId;

    private String probeId;

    private Date collectionTime;

    private String deviceIp;

    private String content;

    // todo 识别采集时间需要
//    public enum Type implements GenericEnum {
//        BASE(Arrays.asList("001", "002", "004", "005"), "基础类型");
//
//        @Getter
//        private final List<String> code;
//
//        @Getter
//        private final String desc;
//
//        Type(List<String> codes, String desc) {
//            this.code = codes;
//            this.desc = desc;
//        }
//    }

}
