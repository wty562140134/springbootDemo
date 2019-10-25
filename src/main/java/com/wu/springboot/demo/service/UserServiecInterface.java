package com.wu.springboot.demo.service;

import com.wu.springboot.demo.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserServiecInterface {

    String upload(String uploadPath, MultipartFile multipartFile) throws IOException;

    String download(String filePath, String fileName, HttpServletResponse response);

    /**
     * 添加一条数据
     *
     * @param data
     */
    String addOne(User data);

    /**
     * 批量添加数据
     *
     * @param data
     */
    String addList(List<User> data);

    /**
     * 更具id删除一条数据
     *
     * @param id
     */
    Map<String, Object> deleteOneById(Integer id);

    /**
     * 根据多个id批量伤处数据
     *
     * @param ids
     */
    Map<String, Object> deleteListById(List<Integer> ids);

    /**
     * 根据id更新一条数据
     *
     * @param data
     */
    Map<String, Object> updateOneById(User data);

    /**
     * 根据多个id批量更新数据
     *
     * @param datas
     */
    Map<String, Object> updateListById(List<User> datas);

    /**
     * 更具名称查找数据
     *
     * @param name
     * @return
     */
    User selectOneByName(String name);

    /**
     * 更具多个名称批量查询数据
     *
     * @param names
     * @return
     */
    List<User> selectListByNames(List<String> names);

}
