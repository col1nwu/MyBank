package priv.cwu.mybank.springboot.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {

    private Integer code;

    private String msg;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object data;

}
