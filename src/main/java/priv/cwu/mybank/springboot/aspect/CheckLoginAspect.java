package priv.cwu.mybank.springboot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import priv.cwu.mybank.springboot.constant.UserConstant;
import priv.cwu.mybank.springboot.dao.entity.User;
import priv.cwu.mybank.springboot.dao.repository.UserRepository;
import priv.cwu.mybank.springboot.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 实现验证用户登录的自定义注解
 *
 * @author 吴岱宗
 * @version 12/30/2021
 */
@Slf4j
@Aspect
@Component
public class CheckLoginAspect {

    @Autowired
    private UserRepository userRepository;


    @Pointcut("@annotation(priv.cwu.mybank.springboot.annotation.CheckLogin)")
    public void pointcut() {}


    @Before("pointcut()")
    public void checkLogin(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnauthorizedException();
        }

        String userId = (String) session.getAttribute(UserConstant.SESSION_TOKEN.msg);
        // 如果session里显示未登录，或者没有测试需要的参数，则需要抛出未登录的异常
        if (StringUtils.isEmpty(userId)) {
            if (request.getParameter(UserConstant.TEST_USER.msg) == null) {
                throw new UnauthorizedException();
            }
            log.warn("目前正处于测试环境。。。");
        } else {
            User user = userRepository.findByUserIdAndIsDeleted(userId, false);
            if (user == null) {
                throw new UnauthorizedException();
            }
        }
    }

}
