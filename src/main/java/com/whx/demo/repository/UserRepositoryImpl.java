package com.whx.demo.repository;

import com.whx.demo.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository{
    //用来生成一个递增的id ，作为用户的唯一编号。
    private  static AtomicLong counterId = new AtomicLong();
    //模拟数据的存储，
    private final ConcurrentHashMap<Long,User> userConcurrentMap = new ConcurrentHashMap<>();
    @Override
    public User saveOrUpdateUser(User user) {
        Long id = user.getId();
        if(id == null){
            id = counterId.incrementAndGet();
            user.setId(id);
        }
        userConcurrentMap.put(id,user);
        return user;
    }

    @Override
    public void deleteUsere(Long id) {
        userConcurrentMap.remove(id);
    }

    @Override
    public User getUserById(Long id) {
        return userConcurrentMap.get(id);
    }

    @Override
    public List<User> userList() {
        return new ArrayList<User>(userConcurrentMap.values());
    }
}
