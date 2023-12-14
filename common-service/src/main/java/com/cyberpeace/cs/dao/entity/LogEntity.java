package com.cyberpeace.cs.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :   SN
 * @date :   2023/12/14 16:55
 * @desc :
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEntity implements Serializable {

    String name;

    Date updateTime;

    String size;

    public enum Module{
        EVAL(0,"管理员实训模块","glysx");
        Integer code;
        String name;
        String en;
        Module(Integer code,String name,String en){
            this.code = code;
            this.name = name;
            this.en = en;
        }
        public String getName(){
            return this.name;
        }
        public Integer getCode(){
            return this.code;
        }
        public String getEn(){
            return this.en;
        }
    }
}
