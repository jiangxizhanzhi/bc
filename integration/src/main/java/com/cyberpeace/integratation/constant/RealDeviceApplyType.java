package com.cyberpeace.integratation.constant;

import lombok.Getter;

public class RealDeviceApplyType {
    public enum Apply {
        ADD(1, "新增申请"),
        DELETE(2, "删除申请");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Apply(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum Allot {

        ALLOT(1, "分配"),
        RECYCLE(2, "回收");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Allot(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
