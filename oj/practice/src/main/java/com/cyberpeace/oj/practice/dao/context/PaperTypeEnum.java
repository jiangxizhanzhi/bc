package com.cyberpeace.oj.practice.dao.context;

public enum PaperTypeEnum {

    /** 职业 */
    PROFESSION_SICHUAN(1, "职业技能竞赛-四川",new TheoryExamProperty(90,50,1, 30,1, 20,1, 0,0,0, 0),new OperationExamProperty(180,new Integer[]{10,5},new Integer[]{10,5}, 5,1));
//    PROFESSION_JIANGSU(2, "职业技能竞赛-江苏",new TheoryExamProperty(180,50, 10, 30, 2, 6),new OperationExamProperty(180,0, 1)),
//    PROFESSION_COUNTRY(3, "职业技能竞赛-全国"),
//    POST_SHANGHAI(3, "岗位认证考试-上海"),
//    POST_ZHEJIANG(3, "岗位认证考试-浙江"),
//    POST_HAINAN(3, "岗位认证考试-海南"),
//    POST_GUANGDONG(3, "岗位认证考试-广东");

    private Integer code;
    private String desc;
    private TheoryExamProperty theoryExamProperty;
    private OperationExamProperty operationExamProperty;

    PaperTypeEnum(Integer code, String desc, TheoryExamProperty theoryExamProperty, OperationExamProperty operationExamProperty) {
        this.code = code;
        this.desc = desc;
        this.theoryExamProperty = theoryExamProperty;
        this.operationExamProperty = operationExamProperty;
    }
}
