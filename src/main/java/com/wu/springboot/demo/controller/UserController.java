package com.wu.springboot.demo.controller;

import com.wu.springboot.demo.entity.User;
import com.wu.springboot.demo.service.impl.UserServiceImpl;
import com.wu.springboot.demo.utils.SuccessMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    // restful
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/user")
    public Map<String, String> add(User user) {
        System.out.println("====================>" + user);
        String msg = userService.addOne(user);
        return SuccessMap.success(msg);
    }

    @PostMapping("/userAddList")
    public Map<String, String> add(@RequestBody List<User> users) {
        System.out.println("====================>" + users);
        String msg = userService.addList(users);
        return SuccessMap.success(msg);
    }

    @DeleteMapping("/user")
    public Map<String, String> remove(Integer id) {
        System.out.println("====================>" + id);
        Map<String, Object> msgMap = userService.deleteOneById(id);
        if (msgMap.containsKey("errorMsg") && msgMap.get("errorMsg").toString().length() > 0) {
            return SuccessMap.failure(msgMap.get("errorMsg").toString());
        }
        return SuccessMap.success(msgMap.get("successMsg").toString());
    }

    @DeleteMapping("/userDeleteList")
    public Map<String, String> remove(@RequestBody List<Integer> ids) {
        System.out.println("====================>" + ids);
        Map<String, Object> msgMap = userService.deleteListById(ids);
        System.out.println(msgMap);
        if (msgMap.containsKey("errorMsg") && msgMap.get("errorMsg").toString().length() > 0) {
            return SuccessMap.failure(msgMap.get("errorMsg").toString());
        }
        return SuccessMap.success(msgMap.get("successMsg").toString());
    }

    @PutMapping("/user")
    public Map<String, String> update(User user) {
        System.out.println("====================>" + user);
        Map<String, Object> msgMap = userService.updateOneById(user);
        if (msgMap.containsKey("errorMsg") && msgMap.get("errorMsg").toString().length() > 0) {
            return SuccessMap.failure(msgMap.get("errorMsg").toString());
        }
        return SuccessMap.success(msgMap.get("successMsg").toString());
    }

    @PutMapping("/userUpdateList")
    public Map<String, String> update(@RequestBody List<User> users) {
        System.out.println("====================>" + users);
        Map<String, Object> msgMap = userService.updateListById(users);
        if (msgMap.containsKey("errorMsg") && msgMap.get("errorMsg").toString().length() > 0) {
            return SuccessMap.failure(msgMap.get("errorMsg").toString());
        }
        return SuccessMap.success(msgMap.get("successMsg").toString());
    }

    @GetMapping("/user")
    public User select(String name) {
        System.out.println("====================>" + name);
        User user = userService.selectOneByName(name);
        return user;
    }

    @GetMapping("/userGetList")
    public List<User> select(@RequestBody List<String> names) {
        System.out.println(">>>>>>>>>>>>>" + names);
        List<User> ents = userService.selectListByNames(names);
        return ents;
    }

    @PostMapping(value = "/upload")
    public Map<String, String> upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        //获取文件原始名称
        System.out.println("Process file: {" + multipartFile.getOriginalFilename() + "}");
        //获取上传webapp下的upload/files目录
        String path = request.getServletContext().getRealPath("/upload/files");
        System.out.println("================>" + path);
        String msg = userService.upload(path, multipartFile);
        if (msg.equals("success")) {
            return SuccessMap.success(msg);
        }
        return SuccessMap.failure(msg);
    }

    @GetMapping("/download")
    public Map<String, String> download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("===============>" + fileName);
        String filePath = request.getServletContext().getRealPath("/upload/files");
        String msg = userService.download(filePath, fileName, response);
        System.out.println("===============>" + msg);
        if (msg.equals("success")) {
            return SuccessMap.success(msg);
        }
        return SuccessMap.failure(msg);
    }

}
