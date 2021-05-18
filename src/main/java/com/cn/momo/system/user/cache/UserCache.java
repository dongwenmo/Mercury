package com.cn.momo.system.user.cache;

import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.user.dto.UserCacheDTO;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * dongwenmo 2021-04-08
 */
public class UserCache {
    private static final LinkedHashMap<String, UserCacheDTO> map = new LinkedHashMap<>();
    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static UserCacheDTO get(String accessToken) throws BusinessException {
        rwl.readLock().lock();
        UserCacheDTO value;
        try {
            value = map.get(accessToken);
        } catch (Exception e) {
            throw new BusinessException("Cache Exception :" + e.getMessage());
        } finally {
            rwl.readLock().unlock();
        }
        return value;
    }

    public static boolean put(String accessToken, UserCacheDTO value) throws BusinessException {
        rwl.writeLock().lock();
        try {
            map.put(accessToken, value);
        } catch (Exception e) {
            throw new BusinessException("Cache Exception :" + e.getMessage());
        } finally {
            rwl.writeLock().unlock();
        }
        return true;
    }

    /**
     * 获取全部
     * dongwenmo 2021-05-18
     */
    public static Map<String, UserCacheDTO> getAll() throws BusinessException {
        Map<String, UserCacheDTO> users = new HashMap<>();
        for(String key:map.keySet()){
            users.put(key, map.get(key));
        }
        return users;
    }
    
    /**
     * 根据accessToken清除缓存
     * dongwenmo 2021-05-18
     */
    public static boolean remove(String accessToken) throws BusinessException{
        rwl.writeLock().lock();
        try {
            map.remove(accessToken);
        } catch (Exception e) {
            throw new BusinessException("Cache Exception :" + e.getMessage());
        } finally {
            rwl.writeLock().unlock();
        }
        return true;
    }

    /**
     * 根据用户信息清除缓存
     * dongwenmo 2021-05-18
     */
    public static boolean remove(UserCacheDTO userCacheDTO) throws BusinessException{
        for(String key:map.keySet()){
            UserCacheDTO user = map.get(key);
            if(userCacheDTO.getAppId().equals(user.getAppId()) && userCacheDTO.getUserId().equals(user.getUserId()) && userCacheDTO.getIp().equals(user.getIp())){
                remove(key);
                return true;
            }
        }
        
        return false;
    }


}
