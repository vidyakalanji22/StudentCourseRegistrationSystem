package com.scr.filter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.google.common.base.Strings;
import com.scr.etag.ApplicationCacheService;
import com.scr.etag.EntityTag;

@Provider
public class EntityTagRequestFilter implements ContainerRequestFilter {
 
  private static final String IF_NONE_MATCH = "If-None-Match";
  private static final String IF_MATCH = "If-Match";
 
  @Context
  private ResourceInfo resourceInfo;
 
  private ApplicationCacheService applicationCacheService = ApplicationCacheService.getInstance();
 
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    if (resourceInfo.getResourceMethod().getAnnotation(EntityTag.class) == null) {
      return;
    }
 
    for (Annotation annotation : resourceInfo.getResourceMethod().getDeclaredAnnotations()) {
      if (annotation instanceof GET) {
        if (isNotModified(requestContext)) {
          requestContext.abortWith(Response.status(Response.Status.NOT_MODIFIED).entity("Data is not modified").build());
          return;
        }
      } else if (annotation instanceof PUT || annotation instanceof DELETE) {
        if (isPreconditionFailed(requestContext)) {
          requestContext.abortWith(Response.status(Response.Status.PRECONDITION_FAILED).entity("Precondition Failed").build());
          return;
        }
      }
    }
  }
 
  /*
   * Returns true if the ETag value in the If-None-Match header matches the ETag
   * of the resource in the server cache. Meaning the requesting client has the
   * latest version of the resource.
   */
  private boolean isNotModified(ContainerRequestContext requestContext) {
    String eTag = getETagFromRequest(requestContext, IF_NONE_MATCH);
    String cachedETag = applicationCacheService.getETag(requestContext);
 
    if (!Strings.isNullOrEmpty(eTag) && !Strings.isNullOrEmpty(cachedETag)) {
      if (eTag.equals(cachedETag)) {
        return true;
      }
    }
    return false;
  }
 
  /*
   * Returns true if the ETag value in the If-Match header does not match the
   * latest ETag of the resource in the server cache. Meaning the requesting
   * client is updating or deleting an out-dated version of the resource.
   */
  private boolean isPreconditionFailed(ContainerRequestContext requestContext) {
    String eTag = getETagFromRequest(requestContext, IF_MATCH);
    String cachedETag = applicationCacheService.getETag(requestContext);
 
    if (!Strings.isNullOrEmpty(eTag) && !Strings.isNullOrEmpty(cachedETag)) {
      if (!eTag.equals(cachedETag)) {
        return true;
      }
    }
    return false;
  }
 
  /*
   * Returns the ETag value in the given header from the provided request
   * context. Returns null if there is no ETag or there are multiple ETAG
   * values.
   */
  private String getETagFromRequest(ContainerRequestContext requestContext, String header) {
    List<String> eTags = requestContext.getHeaders().get(header);
    if (eTags != null && eTags.size() == 1) {
      return eTags.get(0);
    }
    return null;
  }
 
}