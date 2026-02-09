package com.notification.api.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.api.constants.ErrorConstants;
import com.notification.api.dao.interfaces.CacheService;
import com.notification.api.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.notification.api.constants.ApplicationConstants.*;
import static com.notification.api.constants.ErrorConstants.PUT_CACHING_ERROR;


@Service
@AllArgsConstructor
public class CacheServiceImpl implements CacheService {
    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;

    private String getTenantCacheKey(final String tenantID){
        return TEMPLATE_REDIS_PREFIX.concat(tenantID);
    }

    public <T> void putBYid (final String tenantId ,final String id,T data){
      put(tenantId,REDIS_LOOKUP_BY_ID.concat(id),data);
    }

    public <T> void putByName(final String tenantId,final String name ,T data){
      put(tenantId,REDIS_LOOKUP_BY_NAME.concat(name),data);
  }

    public void deleteById(final String tenantID,final String id){
        delete(tenantID,REDIS_LOOKUP_BY_ID.concat(id));
    }

    public void deleteByName(final String tenantID,final String name){
        delete(tenantID,REDIS_LOOKUP_BY_NAME.concat(name));
    }

    //Generic function
    private <T> void put(final String tenantId,final String hashKey,T data){
//      put(redisKey, field, value)

      /*

      tenantId = "tenant_1"
      hashKey  = "BY_ID:101"
      jsonData = "{id:101, name:Welcome}"

       */
        try {
            String jsonDate  = objectMapper.writeValueAsString(data);
            redisTemplate.opsForHash().put(getTenantCacheKey(tenantId),hashKey,jsonDate);
        } catch (JsonProcessingException e) {
            throw new ValidationException(PUT_CACHING_ERROR, HttpStatus.BAD_REQUEST.value());
        }

    }

    private <T> void delete(final String tenantId,final String hashKey){
      redisTemplate.opsForHash().delete(getTenantCacheKey(tenantId),hashKey);
    }

    public <T> void getByID((final String tenantId ,final String name,class<T> data)){

    }
    public <T> void getByName(final String tenantId ,final String name,class<T> data){

    }

    private <T> T get(final String tenantId,final String hashKey,class<T> claszz){

        HashOperations<String,String,String> ops = redisTemplate.opsFor

    }
    }
