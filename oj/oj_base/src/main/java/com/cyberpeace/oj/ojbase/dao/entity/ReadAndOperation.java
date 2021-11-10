package com.cyberpeace.oj.ojbase.dao.entity;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

@Data
public class ReadAndOperation extends BaseTable {

    /** 授权访问*/
    private Integer readAuthType = AuthShare.CUSTOM.code;

    /** 授权操作*/
    private Integer operationAuthType = AuthShare.CUSTOM.code;

    /** 已授权访问组织*/
    private String readAuthIds;

    /** 已授权访问用户*/
    private String operationAuthIds;

    public enum AuthShare implements GenericEnum {

        ALL(1, "共享"), CUSTOM(2, "自定义");

        // xml中被引用 不能改为private
        @Getter
        public Integer code;
        @Getter
        public String desc;

        AuthShare(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }


}
