package com.cyberpeace.integratation.model.situation.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.UserActionContext;

import lombok.Data;

@Data
public class LgUserActionRsp extends LgPageResBase implements Serializable {

	private List<UserActionContext> list;
}
