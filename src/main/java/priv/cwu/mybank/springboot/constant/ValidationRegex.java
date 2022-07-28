package priv.cwu.mybank.springboot.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationRegex {

    EMAIL("^\\w+([-+.]\\w+)*@[a-zA-Z0-9]+\\.[a-zA-Z]+$"),

    MONEY("^[0-9]+(.[0-9]{1,2})?$");


    public final String msg;

}
