package priv.cwu.mybank.springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义注解：用于验证账户的所有权，避免非法操作
 *
 * @author 吴岱宗
 * @version 1/1/2022
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CheckOwnership {

    String value() default "";

}
