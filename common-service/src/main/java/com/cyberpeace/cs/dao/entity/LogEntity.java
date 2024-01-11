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

    Integer module;

    String moduleName;

    public enum Module{
        BC(1,"bc","bc", "/var/log/bc/"),
        AD(2,"ad","ad", "/var/log/ad/"),
        SUPERVISOR(3,"supervisor","supervisor", "/var/log/supervisor/"),
        SCP(4,"scp","scp", "/var/log/scp/"),
        GUNICORN(5,"gunicorn","gunicorn", "/var/log/gunicorn/");
        Integer code;
        String name;
        String en;
        String path;
        Module(Integer code,String name,String en, String path){
            this.code = code;
            this.name = name;
            this.en = en;
            this.path = path;
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
        public String getPath(){
            return this.path;
        }
    }
}
