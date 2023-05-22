package com.trading.system.logic.gateway.utils;

import com.google.gson.Gson;
import com.trading.system.data.OrderInstruction;
import com.trading.system.data.OrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    private Utils() {}

    public static OrderInstruction createOrderInstruction(String symbol, OrderType orderType) {
        OrderInstruction orderInstruction = new OrderInstruction(symbol, orderType);
        LOGGER.info("Order instruction: {}", orderInstruction);

        return orderInstruction;
    }

    public static <T> byte[] serializeUsingGson(T object) {
        LOGGER.info("Serializing object");

        return new Gson().toJson(object).getBytes();
    }
}
