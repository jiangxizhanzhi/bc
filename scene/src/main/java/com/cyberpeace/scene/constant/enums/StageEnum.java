package com.cyberpeace.scene.constant.enums;

import lombok.Getter;

public enum StageEnum {

    INIT("init", "初始化", "init"),
    CREATE_NETWORK("create_network", "创建网络", "network"),
    CREATE_INSTANCE("create_instance", "创建实例", "instance");

    @Getter
    private String stage;
    @Getter
    private String stageName;
    @Getter
    private String icon;

    StageEnum(String stage, String stageName, String icon) {
        this.stage = stage;
        this.stageName = stageName;
        this.icon = icon;
    }

}
