package com.majiang.mgtest.controller;

import com.majiang.mgtest.mapper.QuestionMapper;
import com.majiang.mgtest.mapper.UserMapper;
import com.majiang.mgtest.model.Question;
import com.majiang.mgtest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller

public class PublishController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/dopublish")
    public String dopublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model) {
        Cookie[] cookies = request.getCookies();
        User user = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if (user == null) {
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        if(StringUtils.isEmpty(description)){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(StringUtils.isEmpty(tag)){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        Question question = new Question();
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreate(System.currentTimeMillis());
        question.setMgtModified(question.getCreate());
        questionMapper.create(question);

        return "publish";
    }

}
