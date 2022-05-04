package com.simple.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.pojo.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User>findAllByConfigXML();
}
