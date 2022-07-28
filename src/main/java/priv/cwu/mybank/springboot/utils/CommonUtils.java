package priv.cwu.mybank.springboot.utils;

import org.apache.commons.lang3.StringUtils;
import priv.cwu.mybank.springboot.constant.UserConstant;
import priv.cwu.mybank.springboot.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 通用工具类
 *
 * @author 吴岱宗
 * @version 1/1/2022
 */
public class CommonUtils {

    /**
     * 获取登录的userId
     *
     * @param request http请求
     * @return 登录的userId
     */
    public static String getLoginUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnauthorizedException();
        }

        String userId = (String) session.getAttribute(UserConstant.SESSION_TOKEN.msg);
        if (StringUtils.isEmpty(userId)) {
            userId = request.getParameter(UserConstant.TEST_USER.msg);
            if (StringUtils.isEmpty(userId)) {
                throw new UnauthorizedException();
            }
        }
        return userId;
    }


    /**
     * 生成随机的字符串
     *
     * @return 随机的字符串
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "").substring(0, 32);
    }

}
