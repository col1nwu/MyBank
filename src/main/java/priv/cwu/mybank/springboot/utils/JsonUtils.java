package priv.cwu.mybank.springboot.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();


    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    public static String parseObject(Object obj) {
        return parseObject(obj, false);
    }

    public static String parseObject(Object obj, Boolean ignoreNull) {
        ObjectMapper mapper = new ObjectMapper();
        if (ignoreNull) {
            mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        } else {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        String result = null;
        try {
            result = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }


}
