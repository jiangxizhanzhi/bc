package com.cyberpeace.integratation.model.situation.res;

import com.cyberpeace.integratation.model.situation.TaskContext;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LgTaskContextPageRes extends LgPageResBase implements Serializable {

    private List<TaskContext> list;
}
