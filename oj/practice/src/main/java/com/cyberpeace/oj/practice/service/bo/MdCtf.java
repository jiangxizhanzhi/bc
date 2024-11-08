package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.parser.ListParser;
import lombok.Data;

import java.util.List;

/**
 * MD CTF
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class MdCtf extends MdCtfBase {

    /**
     * 旗
     */
    @MdHeadLine(level = 3, name = "FLAG", parser = ListParser.class)
    private List<String> flag;

}
