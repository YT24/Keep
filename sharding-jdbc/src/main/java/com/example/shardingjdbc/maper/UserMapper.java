package com.keep.shardingjdbc.maper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.keep.shardingjdbc.bean.Goods;
import com.keep.shardingjdbc.bean.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
 
}