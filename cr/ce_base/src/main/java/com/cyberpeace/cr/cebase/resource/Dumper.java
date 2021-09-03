package com.cyberpeace.cr.cebase.resource;

import com.cyberpeace.cr.cebase.model.crevent.CrEvent;

public interface Dumper {

    Representation dump(CrEvent crEvent, Collector collector);
}
