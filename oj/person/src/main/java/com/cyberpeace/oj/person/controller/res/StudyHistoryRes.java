package com.cyberpeace.oj.person.controller.res;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public class StudyHistoryRes implements Serializable {

    private String resourceId;

    private String name;

    private Integer type;

    private String date;

    private String raceId;

    /**
     * 习题 1:理论 2：实操 3:运维
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

    @Getter
    public enum AdEnum {
        EXAM("resource_id", "name", 3, 1, "race_id"),
        OPS("question_id", "question_name", 2, 3, "race_id");
        private String resourceIdName;
        private String name;
        private Integer type;
        private Integer businessType;
        private String raceIdName;


        AdEnum(String resourceIdName, String name, Integer type, Integer businessType, String raceIdName) {
            this.resourceIdName = resourceIdName;
            this.name = name;
            this.type = type;
            this.businessType = businessType;
            this.raceIdName = raceIdName;
        }
    }
}
