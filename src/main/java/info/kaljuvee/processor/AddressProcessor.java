package info.kaljuvee.processor;

import info.kaljuvee.model.UserRecord;

public class AddressProcessor extends FieldProcessor {

    public AddressProcessor(String key) {
        super(key);
    }

    @Override
    public void parseInto(String value, UserRecord record) {
        validateInputs(value, record);
        record.setAddress(value);
    }

    @Override
    public String normalize(UserRecord record) {
        return record.getAddress();
    }
}
