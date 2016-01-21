package com.mm.dev.dao.mapper1;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mm.dev.entity.entity2.User;

/**
 * Created by Lipengfei on 2015/6/26.
 */
public interface UserMapper1 {

    List<User> findAll();

    Page<User> getUserAll(@Param("pageable")Pageable pageable);

    void save(User user);

}
