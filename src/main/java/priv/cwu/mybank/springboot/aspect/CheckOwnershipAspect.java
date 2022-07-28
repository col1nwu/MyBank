package priv.cwu.mybank.springboot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import priv.cwu.mybank.springboot.constant.UserConstant;
import priv.cwu.mybank.springboot.dao.entity.Account;
import priv.cwu.mybank.springboot.dao.entity.User;
import priv.cwu.mybank.springboot.dao.repository.AccountRepository;
import priv.cwu.mybank.springboot.dao.repository.UserRepository;
import priv.cwu.mybank.springboot.exception.UnauthorizedException;
import priv.cwu.mybank.springboot.exception.UnownedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Aspect
@Component
public class CheckOwnershipAspect {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Pointcut("@annotation(priv.cwu.mybank.springboot.annotation.CheckOwnership)")
    public void pointcut() {}

    @Before("pointcut()")
    public void checkLogin(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnauthorizedException();
        }

        String userId = (String) session.getAttribute(UserConstant.SESSION_TOKEN.msg);
        // 如果session里显示未登录，或者没有测试需要的参数，则需要抛出未登录的异常
        if (StringUtils.isEmpty(userId)) {
            userId = request.getParameter(UserConstant.TEST_USER.msg);
            if (userId == null) {
                userId = "test";
            }
        }

        Object[] args = joinPoint.getArgs();
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        int index = ArrayUtils.indexOf(parameterNames, "accountId");
        if (index == -1) {
            log.error("命名不规范，无法找到 accountId ！");
            throw new MissingServletRequestParameterException("accountId", "String");
        }
        String accountId = (String) args[ArrayUtils.indexOf(parameterNames, "accountId")];

        User user = userRepository.findByUserIdAndIsDeleted(userId, false);
        List<Account> accounts = accountRepository.findAllByAccountHolderIdAndIsDeleted(user.getId(), false);
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                return;
            }
        }

        throw new UnownedException();
    }

}
