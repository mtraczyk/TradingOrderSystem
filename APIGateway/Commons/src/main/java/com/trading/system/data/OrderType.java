package com.trading.system.data;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OrderType {
    BUY("BUY"), SELL("SELL"), SHORT("SHORT");

    private final String stringOrderType;
    private static final Map<String, OrderType> stringToEnum =
            Stream.of(values()).collect(Collectors.toMap(Object::toString, e -> e));

    OrderType(String stringOrderType) {
        this.stringOrderType = stringOrderType;
    }

    public static Optional<OrderType> fromString(String stringOrderType) {
        return Optional.ofNullable(stringToEnum.get(stringOrderType));
    }
}
