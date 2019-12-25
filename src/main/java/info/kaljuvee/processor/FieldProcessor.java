package info.kaljuvee.processor;

import info.kaljuvee.model.UserRecord;

/**
 * Generic abstract CSV field processor, which specific processors should extend.
 *
 * @author Oliver Kaljuvee
 */
public abstract class FieldProcessor {

    /**
     * Represents the column name that given processor is responsible for processing
     */
    private String key;

    public FieldProcessor(String key) {
        this.key = key;
    }

    /**
     * Parses the given value stored in a CSV filed and stores into the user record.  Each implementation can choose
     * to parse the field using different rules, including by doing some pre-processing for further normalization.
     *
     * @param value Value from CSV file to be parsed
     * @param record User record for which the value will be set
     */
    public abstract void parseInto(String value, UserRecord record);

    /**
     * Normalizes or applies certain transformations for the class attribute associate with the given processor. This
     * implementation allows any given processor to access other class attributes during the normalization step, if
     * needed.
     *
     * @param record The record which hold the attribute on which the processor applies the normalization
     * @return Normalized value value, i.e. the output value of the field associated with the given processor
     */
    public abstract String normalize(UserRecord record);

    /**
     * Common valiator for validating inputs and which calls the processor-specific validation as well.
     *
     * @param value  Field to be validated
     * @param userRecord User record to be validated
     */
    void validateInputs(String value, UserRecord userRecord) {
        if(value == null || userRecord == null) {
            throw new RuntimeException("Input to processor parsing cannot be null");
        }
        validate(value);
    }

    /**
     * Processor-specific validation, which can be overriden.
     *
     * @param field Field to be validated
     * @see ZipCodeProcessor
     */
    public void validate(String field) {}

    /**
     * Returns the column key string associated with the column that the given processor is responsbile for processing.
     *
     * @return The column name that given processor is responsible for processing
     */
    public String getKey() {
        return key;
    }

    /**
     * Utility function to parse times in HH:MM:SS.MS, where each field can have arbitrary length; so can't use
     * java.time.Duration.
     *
     * @param duration Time duration in house, minutes, second and milliseconds in HH:MM:SS.MS format
     * @return Floating-point value of the duration
     */
    static double parseSeconds(String duration) {
        String[] split = duration.split(":");
        int h = Integer.parseInt(split[0]) * 3600;
        int m = Integer.parseInt(split[1]) * 60;
        double s = Double.parseDouble(split[2]);
        return h + m + s;
    }
}
