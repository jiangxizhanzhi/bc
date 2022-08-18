package com.cyberpeace.common.mdparser;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import lombok.Data;

import java.io.Serializable;

@Data
public class MdOutline implements Serializable {

    @MdHeadLine(level = 2, name = "课时大纲")
    private String outline;

}
