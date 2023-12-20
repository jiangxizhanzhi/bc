package com.cyberpeace.auth.service.bo;

import com.cyberpeace.common.annoations.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2023/12/19 17:44
 * @desc :
 **/
@Data
public class UserExcel implements Serializable {

    @ExcelProperty(index = 0, value = "用户名")
    private String username;

    @ExcelProperty(index = 1, value = "姓名")
    private String name;

    @ExcelProperty(index = 2, value = "密码")
    private String password;
}
