package hu.ksrichard.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * JSON Converting utility for tests
 * @author ksrichard
 */
public class JsonUtil {

    /**
     * Converting any {@link java.lang.Object} to {@link String}
     * @param obj Object to convert to JSON string
     * @return {@link String}
     * @throws IOException
     */
    public static String getJson(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer,obj);
        return writer.toString();
    }

}
