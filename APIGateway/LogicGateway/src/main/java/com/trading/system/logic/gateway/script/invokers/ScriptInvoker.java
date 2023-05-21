package com.trading.system.logic.gateway.script.invokers;

import com.trading.system.data.OrderType;
import com.trading.system.logic.gateway.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ScriptInvoker {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptInvoker.class);

    private ScriptInvoker() {
        throw new IllegalStateException("Utility class");
    }
    public static void invokeBot(String scriptPath) throws Exception {
        // TODO: fetch data from polygon.io and give it as a parameter to the script
        ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String stringOrderType = bfr.readLine();
        String stringSymbol = bfr.readLine();

        LOGGER.info("Exit code of {}: {}", scriptPath, exitCode);
        LOGGER.info("Output of {}: {} {}", scriptPath, stringOrderType, stringSymbol);

        Utils.createAndSendOrderInstruction(stringSymbol, OrderType.fromString(stringOrderType).orElseThrow());
    }
}
