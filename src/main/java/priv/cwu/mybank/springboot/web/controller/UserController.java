package priv.cwu.mybank.springboot.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import priv.cwu.mybank.springboot.annotation.CheckLogin;
import priv.cwu.mybank.springboot.constant.UserConstant;
import priv.cwu.mybank.springboot.dao.entity.User;
import priv.cwu.mybank.springboot.service.UserService;
import priv.cwu.mybank.springboot.utils.CommonUtils;
import priv.cwu.mybank.springboot.utils.HttpResponse;
import priv.cwu.mybank.springboot.utils.ResponseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;

@Slf4j
@Validated
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public HttpResponse login(HttpServletRequest request, HttpServletResponse response,
                              @Validated @Email @RequestParam(value = "email") String email,
                              @RequestParam(value = "password") String password
    ) {
        HttpResponse result = userService.login(email, password);
        if (result.getCode() == 200) {
            User user = (User) result.getData();
            // 为session添加参数
            HttpSession session = request.getSession();
            session.setAttribute(UserConstant.SESSION_TOKEN.msg, user.getUserId());
        }
        return result;
    }


    @CheckLogin
    @RequestMapping("/logout")
    public HttpResponse logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(UserConstant.SESSION_TOKEN.msg);
        return ResponseUtils.newSuccessResult();
    }


    @PostMapping("/user")
    public HttpResponse register(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @Validated @Email @RequestParam("email") String email
    ) {
        return userService.insertUser(username, password, email);
    }


    @CheckLogin
    @DeleteMapping("/user")
    public HttpResponse delete(HttpServletRequest request, HttpServletResponse response
    ) {
        String userId = CommonUtils.getLoginUserId(request);
        return userService.deleteUser(userId);
    }


    @CheckLogin
    @PostMapping("/password")
    public HttpResponse changePassword(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam("password") String password
    ) {
        String userId = CommonUtils.getLoginUserId(request);
        return userService.changePassword(userId, password);
    }

}
