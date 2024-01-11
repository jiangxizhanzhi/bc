package com.cyberpeace.oj.person.controller.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudyHistoryRes implements Serializable {

    private String resourceId;

    private String name;

    private Integer type;

    private String date;

    /**
     * 习题 1:理论 2：实操
     */
    private Integer businessType;

    public enum TypeEnum {

        LESSON(1, "课程"),
        PRACTICE(2, "题目练习"),
        EXAM(3, "考试");
        private Integer code;

        private String desc;

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        TypeEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
