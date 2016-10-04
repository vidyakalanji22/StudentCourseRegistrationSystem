package com.scr.etag;

import javax.ws.rs.container.ContainerRequestContext;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig.MaxSizePolicy;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class ApplicationCacheService {
	 
	  private static ApplicationCacheService _CacheService = new ApplicationCacheService();
	 
	  private HazelcastInstance hazelcastInstance;
	  private IMap<String, String> eTagCache;
	 
	  public static ApplicationCacheService getInstance() {
	    return _CacheService;
	  }
	 
	  private ApplicationCacheService() {
	    JoinConfig joinConfig = new JoinConfig();
	    joinConfig.getMulticastConfig().setEnabled(false);
	    joinConfig.getAwsConfig().setEnabled(false);
	    joinConfig.getTcpIpConfig().setEnabled(true);
	    joinConfig.getTcpIpConfig().addMember("localhost");
	 
	    NetworkConfig networkConfig = new NetworkConfig();
	    networkConfig.setPort(5701);
	    networkConfig.setPortAutoIncrement(false);
	    networkConfig.setJoin(joinConfig);
	 
	    MapConfig mapConfig = new MapConfig();
	    mapConfig.setName("ETAG");
	    mapConfig.setAsyncBackupCount(0);
	    mapConfig.getMaxSizeConfig().setMaxSizePolicy(MaxSizePolicy.USED_HEAP_PERCENTAGE);
	    mapConfig.getMaxSizeConfig().setSize(75);
	    mapConfig.setEvictionPercentage(50);
	 
	    Config config = new Config();
	    config.setNetworkConfig(networkConfig);
	    config.addMapConfig(mapConfig);
	 
	    hazelcastInstance = Hazelcast.newHazelcastInstance(config);
	    eTagCache = hazelcastInstance.getMap("ETAG");
	  }
	 
	  public void shutdown() {
	    hazelcastInstance.shutdown();
	  }
	 
	  public String getETag(ContainerRequestContext requestContext) {
	    String key = getETagCacheKey(requestContext);
	    return eTagCache.get(key);
	  }
	 
	  public void putIfAbsentETag(ContainerRequestContext requestContext, String eTag) {
	    String key = getETagCacheKey(requestContext);
	    eTagCache.putIfAbsent(key, eTag);
	  }
	 
	  public void replaceETag(ContainerRequestContext requestContext, String eTag) {
	    String key = getETagCacheKey(requestContext);
	    eTagCache.replace(key, eTag);
	  }
	 
	  public void removeETag(ContainerRequestContext requestContext) {
	    String key = getETagCacheKey(requestContext);
	    eTagCache.remove(key);
	  }
	 
	  private String getETagCacheKey(ContainerRequestContext requestContext) {
	    StringBuilder sb = new StringBuilder();
	    sb.append(requestContext.getUriInfo().getPath());
	    return sb.toString();
	  }
	}
