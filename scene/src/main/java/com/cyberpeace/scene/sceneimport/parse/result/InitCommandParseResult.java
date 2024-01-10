package com.cyberpeace.scene.sceneimport.parse.result;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author maojun
 * @Date 2021/10/12
 */
@Data
public class InitCommandParseResult {
    private String o;
    private String n;
    private String s;
    @Value("${mp.cscene.enable-command-change:false}")
    private Boolean enableCommandChange;

    public List<String> getAllCommands() {
        List<String> result = new ArrayList<>();
        if (this.o != null) {
            if (Boolean.TRUE.equals(enableCommandChange)) {
                result.add("n:" + this.o);
            } else {
                result.add("o:" + this.o);
            }
        }
        if (this.s != null) {
            result.add("s:" + this.s);
        }
        if (this.n != null) {
            result.add("n:" + this.n);
        }
        return result;
    }
}
