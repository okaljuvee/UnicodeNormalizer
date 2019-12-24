package info.kaljuvee.processor;

import info.kaljuvee.model.UserRecord;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimestampProcessor extends FieldProcessor {

    public TimestampProcessor(String key) {
        super(key);
    }

    /**
     *
     * $Assumption 2: the Timestamp column should be formatted in ISO-8601 format. E.g. 1997-07-16T19:20:30.45+01:00
     */

    @Override
    public void parseInto(String value, UserRecord record)  {

        // TODO: implement
        validateInputs(value, record);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm:ss a");
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(value, formatter);
        } catch(DateTimeParseException e) {
            throw new RuntimeException("Unable to normalize the date field: [" + value + "] " + e.getMessage());
        }

        record.setTimestamp(dateTime.atZone(ZoneId.of("US/Pacific")));
    }

    /**
     *
     * $Assumption 3: the Timestamp column should be assumed to be in US/Pacific time; convert it to US/Eastern.
     *
     * @param record
     * @return
     */
    @Override
    public String normalize(UserRecord record) {
        return record.getTimestamp()
                .withZoneSameInstant(ZoneId.of("US/Eastern"))
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

//    @Override
//    public String getFieldKey() {
//        return "Timestamp";
//    }
}
