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
        record.setFooDuration(parseSecond(value));
    }

    @Override
    public String normalize(UserRecord record) {
        return BigDecimal.valueOf(record.getFooDuration()).toPlainString();
    }
}
