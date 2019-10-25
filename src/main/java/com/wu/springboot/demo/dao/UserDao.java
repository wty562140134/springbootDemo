package com.wu.springboot.demo.dao;

import com.wu.springboot.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    int insert(@Param("data") User data);

    int insertList(@Param("datas") List<User> datas);

    int update(@Param("data") User data);

    int updateList(@Param("datas") List<User> datas);

    int deleteById(@Param("id") Integer id);

    int deleteListById(@Param("ids") List<Integer> ids);

    User selectByName(@Param("name") String name);

    User selectById(@Param("id") Integer id);

    List<User> selectListByName(@Param("names") List<String> names);

    List<User> selectListById(@Param("ids") List<Integer> ids);

}
