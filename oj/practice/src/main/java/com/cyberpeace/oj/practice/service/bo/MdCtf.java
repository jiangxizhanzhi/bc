package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.parser.ListParser;
import lombok.Data;

import java.util.List;

@Data
public class MdCtf extends MdCtfBase {

    @MdHeadLine(level = 3, name = "FLAG", parser = ListParser.class)
    private List<String> flag;

}
