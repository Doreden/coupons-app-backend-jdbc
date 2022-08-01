package com.dor.couponssystem.log;

import java.io.*;

public class LoggerConfig {
    private LoggerConfig() {
    }

    static LogMode logMode;
    static final String LOG_FILE = "logs/log.txt";
    static BufferedWriter writer = null;
    private static final String LOG_CONFIG_FILE = "resources/log_config.txt";

    static {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOG_CONFIG_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("log_mode")) {
                    int index = 0;
                    char ch = line.charAt(index);
                    while (ch != '=') {
                        index++;
                        ch = line.charAt(index);
                    }
                    index++;
                    StringBuilder logModeSb = new StringBuilder();
                    while (index < line.length()) {
                        ch = line.charAt(index);
                        logModeSb.append(ch);
                        index++;
                    }
                    logMode = LogMode.valueOf(logModeSb.toString());
                }
            }
            writer = new BufferedWriter(new FileWriter(LOG_FILE));
        } catch (IOException e) {
            System.out.println("Failed to configure logger");
            logMode = LogMode.CONSOLE;
        }

    }
}
