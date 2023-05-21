package com.trading.system.logic.gateway.script.invokers;

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
        ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String nextLine;
        StringBuilder builderResults = new StringBuilder();
        while ((nextLine = bfr.readLine()) != null) {
            builderResults.append(nextLine);
        }
        String results = builderResults.toString();

        LOGGER.info("Exit code of {}: {}", scriptPath, exitCode);
        LOGGER.info("Output of {}: {}", scriptPath, results);
    }
}
