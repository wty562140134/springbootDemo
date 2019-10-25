package com.wu.springboot.demo.service.impl;

import com.wu.springboot.demo.dao.UserDao;
import com.wu.springboot.demo.entity.User;
import com.wu.springboot.demo.service.UserServiecInterface;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserServiecInterface {

    @Autowired
    private UserDao userDao;


    //临界资源

    @Override
    public String upload(String uploadPath, MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            //文件上传，将文件流拷贝到目标文件对象中
            try {
                FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),
                        new File(uploadPath, System.currentTimeMillis() + multipartFile.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
                return "failure";
            }
            //上传的第二种方式，使用file提供的方法
//            multipartFile.transferTo(new File(path, System.currentTimeMillis()+ file.getOriginalFilename()));
            return "success";
        }
        return "failure";
    }

    @Override
    public String download(String filePath, String fileName, HttpServletResponse response) {
        File file = new File(filePath, filePath);
        OutputStream out = null;
        if (file.exists()) {
            //设置下载完毕不打开文件
            response.setContentType("application/force-download");
            //设置文件名
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            try {
                out = response.getOutputStream();
                out.write(FileUtils.readFileToByteArray(file));
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                return "failure";
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "success";
    }

    @Override
    public String addOne(User data) {
        int success = userDao.insert(data);
        StringBuilder msg = new StringBuilder("添加了[").append(success).append("]条数据");
        System.out.println(msg);
        return msg.toString();
    }

    @Override
    public String addList(List<User> datas) {
        int success = userDao.insertList(datas);
        StringBuilder msg = new StringBuilder("添加了[").append(success).append("]条数据");
        System.out.println(msg);
        return msg.toString();
    }

    @Override
    public Map<String, Object> deleteOneById(Integer id) {
        Map<String, Object> msg = verifyThatTheDataExistsById(id);
        int success = userDao.deleteById((Integer) msg.get("ids"));
        StringBuilder successMsg = new StringBuilder("删除了[").append(success).append("]条数据");
        System.out.println(successMsg);
        msg.put("successMsg", successMsg);
        return msg;
    }

    @Override
    public Map<String, Object> deleteListById(List<Integer> ids) {
        Map<String, Object> msg = verifyThatTheDataExistsById(ids);
        System.out.println(msg);
        int success = userDao.deleteListById((List<Integer>) msg.get("ids"));
        StringBuilder successMsg = new StringBuilder("删除了[").append(success).append("]条数据");
        System.out.println(successMsg);
        msg.put("successMsg", successMsg);
        return msg;
    }

    @Override
    public Map<String, Object> updateOneById(User data) {
        Map<String, Object> msg = verifyThatTheDataExistsById(data.getId());
        int success = 0;
        if (msg.get("ids") != null) {
            success = userDao.update(data);
        }
        StringBuilder successMsg = new StringBuilder("更新了[").append(success).append("]条数据");
        System.out.println(successMsg);
        msg.put("successMsg", successMsg);
        return msg;
    }

    @Override
    public Map<String, Object> updateListById(List<User> datas) {
        List<Integer> ids = new ArrayList<>();
        for (User data : datas) {
            ids.add(data.getId());
        }
        Map<String, Object> msg = verifyThatTheDataExistsById(ids);
        int success = userDao.updateList(datas);
        StringBuilder successMsg = new StringBuilder("更新了[").append(success).append("]条数据");
        System.out.println(successMsg);
        msg.put("successMsg", successMsg);
        return msg;
    }

    @Override
    public User selectOneByName(String name) {
        User ent = userDao.selectByName(name);
        return ent;
    }

    @Override
    public List<User> selectListByNames(List<String> names) {
        List<User> ents = userDao.selectListByName(names);
        return ents;
    }

    private <T> Map<String, Object> verifyThatTheDataExistsById(T id) {
        //返回的datas为id或者ids
        Map<String, Object> result = new HashMap<>();
        StringBuilder msg = new StringBuilder();
        if (id.getClass() == Integer.class) {
            User dataBaseEnt = userDao.selectById((Integer) id);
            if (dataBaseEnt == null) {
                msg.append("id为:[").append(id).append("]的用户不存在").append("\n");
                result.put("errorMsg", msg);
            }
            result.put("ids", dataBaseEnt.getId());
        }
        if (List.class.isInstance(id)) {
            List<User> dataBaseEnt = userDao.selectListById((List<Integer>) id);
            List<Integer> ids = new ArrayList<>();
            List<Integer> errorIds = new ArrayList<>();
            for (User ent : dataBaseEnt) {
                if (ent == null) {
                    msg.append("id为:[").append(id).append("]的用户不存在").append("\n");
                    errorIds.add(ent.getId());
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>" + msg);
                }
                ids.add(ent.getId());
            }
            if (msg.toString().length() > 0) {
                result.put("errorMsg", msg);
                result.put("errorIds", errorIds);
            }
            result.put("ids", ids);
        }
        return result;
    }
}
