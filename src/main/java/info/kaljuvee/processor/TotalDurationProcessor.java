package info.kaljuvee.processor;

import info.kaljuvee.model.UserRecord;

import java.math.BigDecimal;

public class TotalDurationProcessor extends FieldProcessor {

    public TotalDurationProcessor(String key) {
        super(key);
    }

    @Override
    public void parseInto(String value, UserRecord record) {}

    @Override
    public String normalize(UserRecord record) {
        return BigDecimal.valueOf(record.getFooDuration()).add(BigDecimal.valueOf(record.getBarDuration())).toPlainString();
    }
}
