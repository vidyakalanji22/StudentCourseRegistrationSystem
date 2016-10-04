package com.scr.filter;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath("/")
public class ApplicationResourceConfig extends ResourceConfig {

	public ApplicationResourceConfig() {
		packages("com.scr.resources");
		register(RolesAllowedDynamicFeature.class);
	}


}
