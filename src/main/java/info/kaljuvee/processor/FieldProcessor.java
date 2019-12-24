package info.kaljuvee.processor;

import info.kaljuvee.model.UserRecord;

import java.lang.reflect.Field;

public abstract class FieldProcessor {

    private String key;

    public FieldProcessor(String key) {
        this.key = key;
    }

    public abstract void parseInto(String value, UserRecord record);

    public abstract String normalize(UserRecord record);

    //public abstract String getFieldKey();

    public void validateInputs(String value, UserRecord userRecord) {

        // TODO: write generic validator for missing fields etc.

        //getFieldKey();

        // Perform specific processor's validation
        validate(value);
    }

    public void validate(String field) {}

    public String getKey() {
        return key;
    }

    /**
     * // Parse times in HH:MM:SS.MS, where each field can have arbitrary length; so can't use java.time.Duration
     *
     * @param duration
     * @return
     */
    public static double parseSecond(String duration) {
        String[] split = duration.split(":");
        int h = Integer.parseInt(split[0]) * 3600;
        int m = Integer.parseInt(split[1]) * 60;
        double s = Double.parseDouble(split[2]);
        return h + m + s;
    }
}
