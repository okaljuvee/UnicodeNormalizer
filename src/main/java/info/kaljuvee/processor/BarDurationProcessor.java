package info.kaljuvee.processor;

import info.kaljuvee.model.UserRecord;

import java.math.BigDecimal;

public class BarDurationProcessor extends FieldProcessor {

    public BarDurationProcessor(String key) {
        super(key);
    }

    @Override
    public void parseInto(String value, UserRecord record) {
        validateInputs(value, record);
        record.setBarDuration(parseSecond(value));
    }

    @Override
    public String normalize(UserRecord record) {
        return BigDecimal.valueOf(record.getBarDuration()).toPlainString();
    }
}
