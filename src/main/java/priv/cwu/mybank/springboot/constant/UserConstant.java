package priv.cwu.mybank.springboot.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserConstant {

    TEST_USER("__TEST_USER__"),

    SESSION_TOKEN("USER_ID");

    public final String msg;
}
