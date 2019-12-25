package info.kaljuvee.processor;

import info.kaljuvee.model.UserRecord;

import java.util.Locale;

public class NameProcessor extends FieldProcessor {

    public NameProcessor(String key) {
        super(key);
    }

    @Override
    public void parseInto(String value, UserRecord record) {
        validateInputs(value, record);
        record.setFullName(value);
    }

    @Override
    public String normalize(UserRecord record) {
        // The processor converts all names to all upper-case letters
        return record.getFullName().toUpperCase(Locale.getDefault());
    }
}
