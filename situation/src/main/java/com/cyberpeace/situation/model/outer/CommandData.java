package com.cyberpeace.situation.model.outer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommandData implements Serializable {

    @JsonProperty("dataOutput")
    private Object dataOutput;

    @JsonProperty("dataInput")
    private Object dataInput;

}
