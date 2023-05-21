package com.trading.system.logic.gateway.utils;

import com.trading.system.data.OrderInstruction;
import com.trading.system.data.OrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    private Utils() {}

    public static void createAndSendOrderInstruction(String symbol, OrderType orderType) {
        OrderInstruction orderInstruction = new OrderInstruction(symbol, orderType);
        LOGGER.info("Order instruction: {}", orderInstruction);

        // TODO: send order instruction to the IBGateway via MQ
    }
}
