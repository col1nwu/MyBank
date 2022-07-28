package priv.cwu.mybank.springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义注解：用于用户登录验证，防止非法访问
 *
 * @author 吴岱宗
 * @version 12/30/2021
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CheckLogin {

    String value() default "";

}
