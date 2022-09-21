package com.cyberpeace.common.constant;

public interface ShellCommand {

	String RESTART_TOMCAT = "#!/bin/bash" + System.lineSeparator()
			+ "sh /usr/local/tomcat/bin/shutdown.sh && sleep 5s && sh /usr/local/tomcat/bin/startup.sh";

	String EXEC_SH = "nohup sh %s &";
}
