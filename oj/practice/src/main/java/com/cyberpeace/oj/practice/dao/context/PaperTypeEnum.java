package com.cyberpeace.oj.practice.dao.context;

public enum PaperTypeEnum {

    /** 职业 */
    PROFESSION_SICHUAN(1, "职业技能竞赛-四川"),
    PROFESSION_JIANGSU(2, "职业技能竞赛-江苏"),
    PROFESSION_COUNTRY(3, "职业技能竞赛-全国"),
    POST_SHANGHAI(3, "岗位认证考试-上海"),
    POST_ZHEJIANG(3, "岗位认证考试-浙江"),
    POST_HAINAN(3, "岗位认证考试-海南"),
    POST_GUANGDONG(3, "岗位认证考试-广东");

    private Integer code;
    private String desc;
    PaperTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
