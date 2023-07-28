package com.cyberpeace.common.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.enums.SceneEvent;
import com.cyberpeace.common.enums.SceneEventStatus;
import lombok.Getter;

public enum SceneStatusEnum implements GenericEnum {

    /***/
    INIT(0, "未启动", SceneEvent.DEFAULT, SceneEventStatus.INITIALIZE),
    START(1, "启动", SceneEvent.CREATE, SceneEventStatus.FINISH),
    DESTROY(2, "删除", SceneEvent.CLOSE, SceneEventStatus.FINISH),
    PAUSE(3, "暂停", SceneEvent.PAUSE, SceneEventStatus.FINISH);

    @Getter
    private final Integer code;

    @Getter
    private final String desc;

    private final SceneEvent event;

    private final SceneEventStatus eventStatus;

    SceneStatusEnum(Integer code, String desc, SceneEvent event, SceneEventStatus eventStatus) {
        this.code = code;
        this.desc = desc;
        this.event = event;
        this.eventStatus = eventStatus;
    }

    public Boolean isCurrent(Integer event, Integer eventStatus) {
        return event.intValue() == this.event.getCode().intValue()
                && eventStatus.intValue() == this.eventStatus.getCode().intValue();
    }

    public Boolean isClose() {
        return this.code.intValue() == DESTROY.code.intValue();
    }

}
