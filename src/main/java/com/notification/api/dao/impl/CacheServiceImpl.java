package com.notification.api.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.api.dao.interfaces.CacheService;
import com.notification.api.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;
import static com.notification.api.constants.ApplicationConstants.*;
import static com.notification.api.constants.ErrorConstants.CACHE_PARSING_ERROR;
import static com.notification.api.constants.ErrorConstants.PUT_CACHING_ERROR;


@Service
@AllArgsConstructor
public class CacheServiceImpl implements CacheService {
    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * get tenant cache key
     *
     * @param tenantID tenantID
     * @return {@link String}
     * @see String
     */
    private String getTenantCacheKey(final String tenantID){
        return TEMPLATE_REDIS_PREFIX.concat(tenantID);
    }

    /**
     * put b yid
     *
     * @param tenantId tenantId
     * @param id id
     * @param data data
     */
    public <T> void putBYid (final String tenantId ,final String id,T data){
      put(tenantId,REDIS_LOOKUP_BY_ID.concat(id),data);
    }

    /**
     * put by name
     *
     * @param tenantId tenantId
     * @param name name
     * @param data data
     */
    public <T> void putByName(final String tenantId,final String name ,T data){
      put(tenantId,REDIS_LOOKUP_BY_NAME.concat(name),data);
  }

    /**
     * delete by id
     *
     * @param tenantID tenantID
     * @param id id
     */
    public void deleteById(final String tenantID,final String id){
        delete(tenantID,REDIS_LOOKUP_BY_ID.concat(id));
    }

    /**
     * delete by name
     *
     * @param tenantID tenantID
     * @param name name
     */
    public void deleteByName(final String tenantID,final String name){
        delete(tenantID,REDIS_LOOKUP_BY_NAME.concat(name));
    }

    /**
     * put
     *
     * @param tenantId tenantId
     * @param hashKey hashKey
     * @param data data
     */ //Generic function
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

    /**
     * delete
     *
     * @param tenantId tenantId
     * @param hashKey hashKey
     */
    private <T> void delete(final String tenantId,final String hashKey){
      redisTemplate.opsForHash().delete(getTenantCacheKey(tenantId),hashKey);
    }

    /**
     * get by i d
     *
     * @param tenantId tenantId
     * @param id id
     * @param clazz clazz
     * @return {@link Optional}
     * @see Optional
     * @see T
     */
    public <T> Optional<T> getByID(final String tenantId , final String id, Class<T> clazz){

        return get(tenantId,REDIS_LOOKUP_BY_ID.concat(id),clazz);
    }
    /**
     * get by name
     *
     * @param tenantId tenantId
     * @param name name
     * @param clazz clazz
     * @return {@link Optional}
     * @see Optional
     * @see T
     */
    public <T> Optional<T> getByName(final String tenantId , final String name, Class<T> clazz){

        return get(tenantId,REDIS_LOOKUP_BY_NAME.concat(name),clazz);
    }

    /**
     * get
     *
     * @param tenantId tenantId
     * @param hashKey hashKey
     * @param clazz clazz
     * @return {@link Optional}
     * @see Optional
     * @see T
     */
    private <T> Optional<T> get(final String tenantId, final String hashKey, Class<T> clazz){

        HashOperations<String,String,String> ops = redisTemplate.opsForHash();
        String jsondata = ops.get(getTenantCacheKey(tenantId), hashKey);
        try {
            T t = objectMapper.readValue(jsondata, clazz);
            return  Optional.ofNullable(t);
        } catch (JsonProcessingException e) {
            throw new ValidationException(CACHE_PARSING_ERROR,HttpStatus.BAD_REQUEST.value());
        }

    }
    }
