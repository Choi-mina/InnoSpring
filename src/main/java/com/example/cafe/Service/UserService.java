package com.example.cafe.Service;

import com.example.cafe.util.EncryptionUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private HashOperations<String, String, String> hashOps;

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    public void saveUserData(String email, String password, String sessionId) throws Exception {
        String encryptedPassword = EncryptionUtil.encrypt(password);
        String key = "user:session:" + email;
        hashOps.put(key, "sessionId", sessionId);
        hashOps.put(key, "password", encryptedPassword);
    }

    public String getSessionId(String email) {
        String key = "user:session:" + email;
        return hashOps.get(key, "sessionId");
    }

    public String getPassword(String email) throws Exception {
        String key = "user:session:" + email;
        String encryptedPassword = hashOps.get(key, "password");
        return EncryptionUtil.decrypt(encryptedPassword);
    }

    public void deleteUserData(String email) {
        String key = "user:session:" + email;
        hashOps.delete(key, "sessionId", "password");
    }
}
