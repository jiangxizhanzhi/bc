package com.cyberpeace.situation.constant.dashboard;

import lombok.Getter;

import java.util.Arrays;

public enum DashboardPath {

    WHITE("6c326a5599ab4c79a5e3b6e438be23ee", "定制态势-红蓝对抗-白方", "/cr/situation/screen3/red-blue/"),
    RED("541d31f7784446e0bd39f437c152ebd9","定制态势-红蓝对抗-红方", "/cr/situation/screen3/red/"),
    BLUE("a3a413eca3ed41bbb5eb80e6111dfb3a", "定制态势-红蓝对抗-蓝方", "/cr/situation/screen3/blue/"),
    ATTACK("38b8330dfdf24211822699ffc8adfcf3", "定制态势-作战试验-攻击", "/cr/situation/screen3/defense/"),
    DEFENCE("df7d7d4e853742d2acff9f161be3649e", "定制态势-作战实验-防御", "/cr/situation/screen3/attack/"),
    AUTONOMOUS_TRAINING("a2c7fb19871442aba80d65e4c5caa3be", "定制态势-自主训练", "/cr/situation/screen3/autonomous-training/"),
    FLAG_RACE("f06014630820444b9b920eb4154c5672", "定制态势-夺旗竞赛", "/cr/situation/screen3/jeo/"),
    SPECIAL_CONTEST("536b2bcfd1c44874935ee24a2b7cbbd2", "定制态势-专项比武", "/cr/situation/screen3/ad/");

    @Getter
    private String code;

    @Getter
    private String desc;

    @Getter
    private String path;

    DashboardPath(String code, String desc, String path) {
        this.code = code;
        this.desc = desc;
        this.path = path;
    }

    public static <T extends DashboardPath> T getEnumByCode(String code, Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getCode().equals(code))
                .findFirst().orElse(null);
    }
}
