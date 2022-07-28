package priv.cwu.mybank.springboot.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 自定义的错误代码
 *
 * @author 吴岱宗
 * @version 1/1/2022
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    // 格式错误
    WRONG_MONEY_FORMAT(101, "金钱格式错误！"),
    WRONG_EMAIL_FORMAT(102, "邮箱格式错误！"),


    // 创建用户时的错误
    INVALID_USER(1001, "用户不存在！"),
    INVALID_PASSWORD(1002, "密码错误"),
    DUPLICATE_EMAIL(1100, "创建用户时错误！邮箱已经被注册。。。"),


    // 创建账户时的错误
    INVALID_ACCOUNT(2001, "账户不存在！"),
    TOO_MANY_ACCOUNTS(2002, "创建账户时错误！账户数量已达上限。。。"),
    ACCOUNT_LOCKED(2003, "账户被冻结，无法进行操作！"),
    OWNERSHIP_ERROR(2004, "未持有该账户！"),
    NOT_ENOUGH_MONEY(2010, "账户余额不足！");


    public final Integer code;

    public final String msg;

}
