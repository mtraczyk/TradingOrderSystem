package utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    private Utils() {}

    public static <T> byte[] serializeUsingGson(T object) {
        LOGGER.info("Serializing object");

        return new Gson().toJson(object).getBytes();
    }

    public static <T> T deserializeUsingGson(byte[] message, Class<T> clazz) {
        LOGGER.info("Deserializing object");

        return new Gson().fromJson(new String(message), clazz);
    }
}
