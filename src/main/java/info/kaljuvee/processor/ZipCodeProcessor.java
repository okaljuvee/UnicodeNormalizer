package info.kaljuvee.processor;

import info.kaljuvee.model.UserRecord;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ZipCodeProcessor extends FieldProcessor {

    public ZipCodeProcessor(String key) {
        super(key);
    }

    @Override
    public void parseInto(String value, UserRecord record)  {
        validateInputs(value, record);
        // Pad zip codes with 0-prefix if original value has length < 5
        record.setZip(IntStream.range(0, 5-value.length()).mapToObj(i -> "0").collect(Collectors.joining()) + value);
    }

    @Override
    public String normalize(UserRecord record) {
        return record.getZip();
    }

    @Override
    public void validate(String field) {
        if(field.length() < 1) {
            throw new RuntimeException("Invalid ZIP code length of " + field.length() + ", expected 1");
        }

        if(!field.matches("[0-9]+")) {
            throw new RuntimeException("ZIP code string should only contain digits, found: " + field);
        }
    }
}
