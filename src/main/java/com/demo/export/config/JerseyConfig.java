package com.demo.export.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.demo.export.resource.ExportResource;

import ch.qos.logback.core.net.SocketConnector.ExceptionHandler;

/**
 * Jersey restful service configuration
 * @author Neo.Li
 */
@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(ExportResource.class); // export log resource
		register(ExceptionHandler.class); // handle exception
	}

}