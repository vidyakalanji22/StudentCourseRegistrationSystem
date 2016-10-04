package com.scr.filter;
import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.scr.permissions.PermissionsTag;
import com.scr.vo.ErrorMessage;
import com.scr.vo.StudentVO;

@Provider
public class PermissionTagRequestFilter implements ContainerRequestFilter {
 
  @Context
  private ResourceInfo resourceInfo;
  
  @Context
  HttpServletRequest webRequest;
  
  
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    if (resourceInfo.getResourceMethod().getAnnotation(PermissionsTag.class) == null) {
    	return;
    }
 
    for (Annotation annotation : resourceInfo.getResourceMethod().getDeclaredAnnotations()) {
      if (annotation instanceof PermissionsTag) {
    	
        if (!hasPermission(((PermissionsTag) annotation).value())) {
        	ErrorMessage errorMessage =  new ErrorMessage(403, "Please Login");
          requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity(errorMessage).build());
          return;
        }
      }
    }
  }

/*
   * Returns true if the current role of user and session user's roles match
   */
  private boolean hasPermission(String[] roles) {
	StudentVO student = (StudentVO)webRequest.getSession().getAttribute("student");
	boolean permission = false;
	for (String role : roles) {
		if(student!=null){
			permission = student.getUserFlag().trim().equalsIgnoreCase(role);
			if(permission)
				break;
		}
	}
    return permission;
  }
 
 
}