package info.kaljuvee.processor;

import info.kaljuvee.model.UserRecord;

import java.math.BigDecimal;

public class TotalDurationProcessor extends FieldProcessor {

    public TotalDurationProcessor(String key) {
        super(key);
    }

    @Override
    public void parseInto(String value, UserRecord record) {
        // The total duration is set to be the sum of "foo" and "bar" totals
        double duration = BigDecimal.valueOf(record.getFooDuration()).add(BigDecimal.valueOf(record.getBarDuration())).doubleValue();
        record.setTotalDuration(duration);
    }

    @Override
    public String normalize(UserRecord record) {
        return BigDecimal.valueOf(record.getTotalDuration()).toPlainString();
    }
}
