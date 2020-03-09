package com.majiang.mgtest.controller;

import com.majiang.mgtest.dto.AccessTokenDTO;
import com.majiang.mgtest.dto.GithubUser;
import com.majiang.mgtest.mapper.UserMapper;
import com.majiang.mgtest.model.User;
import com.majiang.mgtest.provider.GithupProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithupProvider githupProvider;

    @Value("${githup.client.id}")
    private String clientId;

    @Value("${githup.client.secret}")
    private String clientsecret;

    @Value("${githup.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientsecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githupProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githupProvider.getUser(accessToken);
        if(githubUser!=null){
            User user = new User();
            String token = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setCreateTime(System.currentTimeMillis());
            user.setModified(user.getCreateTime());
            user.setName(githubUser.getName());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
}
