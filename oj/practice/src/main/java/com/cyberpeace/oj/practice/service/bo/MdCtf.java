package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.parser.ListParser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * MD CTF
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see MdCtfBase
 * @since 1.0.0
 */
@Getter
@Setter
public class MdCtf extends MdCtfBase {

    /**
     * 旗
     *
     * @see List<String>
     */
    @MdHeadLine(level = 3, name = "FLAG", parser = ListParser.class)
    private List<String> flag;

}
