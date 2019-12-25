package info.kaljuvee.processor;

import info.kaljuvee.model.UserRecord;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimestampProcessor extends FieldProcessor {

    public TimestampProcessor(String key) {
        super(key);
    }

    @Override
    public void parseInto(String value, UserRecord record)  {
        validateInputs(value, record);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm:ss a");
        LocalDateTime dateTime;

        try {
            dateTime = LocalDateTime.parse(value, formatter);
        } catch(DateTimeParseException e) {
            throw new RuntimeException("Unable to normalize the date field: [" + value + "] " + e.getMessage());
        }
        // All timestamps are assumed to be in PST
        record.setTimestamp(dateTime.atZone(ZoneId.of("US/Pacific")));
    }

    @Override
    public String normalize(UserRecord record) {
        return record.getTimestamp()
                // Normalization step writes timestamps in EST
                .withZoneSameInstant(ZoneId.of("US/Eastern"))
                // Time stamps are formatted in ISO-8601 format
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
