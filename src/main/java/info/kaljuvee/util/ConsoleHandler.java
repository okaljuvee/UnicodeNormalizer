package info.kaljuvee.util;

import java.util.logging.*;

/**
 * Helper class that directs logging to standard out (by default Java Logging prints to standard err). Logic from:
 *
 * https://stackoverflow.com/questions/194165/how-do-i-change-java-logging-console-output-from-std-err-to-std-out
 */
public class ConsoleHandler extends StreamHandler {

    public ConsoleHandler() {
        super(System.out, new SimpleFormatter());
    }

    @Override
    public void publish(LogRecord record) {
        if (getFormatter() == null)  {
            setFormatter(new SimpleFormatter());
        }

        try {
            String message = getFormatter().format(record);

            if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
                System.err.write(message.getBytes());
            } else {
                System.out.write(message.getBytes());
            }
        } catch (Exception exception) {
            reportError(null, exception, ErrorManager.FORMAT_FAILURE);
        }
    }
}
