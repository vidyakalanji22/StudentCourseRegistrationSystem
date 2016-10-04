package com.scr.filter;


import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.scr.etag.ApplicationCacheService;
import com.scr.etag.EntityTag;

@Provider
public class EntityTagResponseFilter implements ContainerResponseFilter {
 
  private static final String CACHE_CONTROL = "Cache-Control";
  private static final String ENTITY_TAG = "ETag";
 
  @Context
  private ResourceInfo resourceInfo;
 
  private ApplicationCacheService applicationCacheService = ApplicationCacheService.getInstance();
 
  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
    if (resourceInfo.getResourceMethod() == null || resourceInfo.getResourceMethod().getAnnotation(EntityTag.class) == null) {
      return;
    }
 
    if (responseContext.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
      return;
    }
 
    for (Annotation annotation : resourceInfo.getResourceMethod().getDeclaredAnnotations()) {
      if (annotation instanceof GET) {
        addETagIfAbsent(requestContext, responseContext);
        return;
      } else if (annotation instanceof PUT) {
        updateETag(requestContext, responseContext);
        return;
      } else if (annotation instanceof DELETE) {
        removeETag(requestContext);
        return;
      }
    }
  }
 
  /*
   * Adds ETag to the response header and adds it to the server cache if it does
   * not exist.
   */
  private void addETagIfAbsent(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
	    Object entity = responseContext.getEntity();

	    if (entity != null) {
	      String eTag = String.valueOf(entity.hashCode());
	      applicationCacheService.putIfAbsentETag(requestContext, eTag);
	      responseContext.getHeaders().add(ENTITY_TAG, applicationCacheService.getETag(requestContext));
	    }
	    responseContext.getHeaders().add(CACHE_CONTROL, "no-cache");
  }
 
  /*
   * Adds ETag to the response header and replaces it on the server cache.
   */
  private void updateETag(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
    Object entity = responseContext.getEntity();
    if (entity != null) {
      String eTag = String.valueOf(entity.hashCode());
      responseContext.getHeaders().add(ENTITY_TAG, eTag);
      applicationCacheService.replaceETag(requestContext, eTag);
    }
    responseContext.getHeaders().add(CACHE_CONTROL, "no-cache");
  }
 
  /*
   * Removes ETag from the server cache.
   */
  private void removeETag(ContainerRequestContext requestContext) {
    applicationCacheService.removeETag(requestContext);
  }
}