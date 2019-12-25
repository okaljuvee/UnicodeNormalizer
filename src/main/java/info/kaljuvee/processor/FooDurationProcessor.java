package info.kaljuvee.processor;

import info.kaljuvee.model.UserRecord;

import java.math.BigDecimal;

public class FooDurationProcessor extends FieldProcessor {

    public FooDurationProcessor(String key) {
        super(key);
    }

    @Override
    public void parseInto(String value, UserRecord record) {
        validateInputs(value, record);
        // Parse the time duration into seconds in floating-point value
        record.setFooDuration(parseSeconds(value));
    }

    @Override
    public String normalize(UserRecord record) {
        return BigDecimal.valueOf(record.getFooDuration()).toPlainString();
    }
}
