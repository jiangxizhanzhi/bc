package com.cyberpeace.oj.person.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import lombok.Data;

@Data
public class PageMistakeReq extends PageReqBase {
    private Integer sortByIncorrect = 0;
    private Integer sortByCorrect = 0;

}
