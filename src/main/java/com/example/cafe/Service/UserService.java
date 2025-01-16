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

    public void saveUserData(String email, String password, String flag, String date) throws Exception {
        String encryptedPassword = EncryptionUtil.encrypt(password);    // 비밀번호 암호화
        String key = "user:session:" + email;
        hashOps.put(key, "password", encryptedPassword);
        hashOps.put(key, "flag", flag);
        hashOps.put(key, "date", date);
    }

    public String getPassword(String email) throws Exception {
        String key = "user:session:" + email;
        String encryptedPassword = hashOps.get(key, "password");    // 비밀번호 복호화
        if(encryptedPassword != null) { // redis에 key-value가 있는 경우
            return EncryptionUtil.decrypt(encryptedPassword);
        }
        return null;
    }

    public String getFlag(String email) throws Exception {
        String key = "user:session:" + email;
        String flag = hashOps.get(key, "flag");
        return flag;
    }

    public String getDate(String email) throws Exception {
        String key = "user:session:" + email;
        String date = hashOps.get(key, "date");
        return date;
    }

    public void deleteUserData(String email) {
        String key = "user:session:" + email;
        hashOps.delete(key, "sessionId", "password", "flag", "date");
    }
}
