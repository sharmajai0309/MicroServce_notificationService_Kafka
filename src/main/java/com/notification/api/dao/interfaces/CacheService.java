package com.notification.api.dao.interfaces;

import java.util.Optional;

public interface CacheService {
    String getTenantCacheKey(String tenantID);

    <T> void putBYid (String tenantId, String id, T data);

    <T> void putByName(String tenantId, String name, T data);

    void deleteById(String tenantID, String id);

    void deleteByName(String tenantID, String name); //Generic function

    <T> void put(String tenantId, String hashKey, T data);

    <T> void delete(String tenantId, String hashKey);

    <T> Optional<T> getByID(String tenantId, String id, Class<T> clazz);

    <T> Optional<T> getByName(String tenantId, String name, Class<T> clazz);

    <T> Optional<T> get(String tenantId, String hashKey, Class<T> clazz);
}
