package com.cyberpeace.cr.cebase.resource;

public interface ResourceConfig {

	int getCeType();

	String getLabel();

	Dumper getDumper();

	Loader getLoader();

}
