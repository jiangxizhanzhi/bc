package com.cyberpeace.situation.constant;

import com.cyberpeace.common.util.StreamApiUtils;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

public enum Event {
    /**
     * 事件
     */

    UNUSE(-1, "未使用"),
    DEFAULT(0, "默认"),
    INITIATE(1, "初始化"),
    CREATE(2, "创建"),
    START(3, "启动"),
    STOP(4, "停止"),
    RESTART(5, "重启"),
    PAUSE(6, "挂起"),
    UNPAUSE(7, "恢复"),
    SNAPSHOT(8, "快照"),
    DELETE(9, "删除"),
    MODIFY(10, "修改"),
    SUSPEND(12, "暂停"),
    RESUME(13, "恢复");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    Event(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static List<Integer> getUnstartedCodeList() {
        return Lists.newArrayList(UNPAUSE.code, STOP.code, PAUSE.code, DELETE.code, SUSPEND.code);
    }

    public static List<Integer> getCreatingCodeList() {
        return Lists.newArrayList(CREATE.code, MODIFY.code);
    }

    public static List<Integer> getStartingCodeList() {
        return Lists.newArrayList(START.code, RESTART.code, UNPAUSE.code, RESUME.code);
    }

}
