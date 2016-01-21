package com.mm.dev.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mm.dev.entity.entity1.User;

/**
 * Created by Lipengfei on 2015/6/26.
 */
public interface UserService {

    User getUser(Long id);

    Page<User> getAll(Pageable pageable);

    List<User> getUserList();

    Page<User> getUserAll(Pageable pageable);

    void save();

    void saveUser();
    
    List<com.mm.dev.entity.entity2.User> getUserList1();

}
