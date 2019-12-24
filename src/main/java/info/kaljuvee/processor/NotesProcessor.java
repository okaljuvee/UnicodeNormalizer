package info.kaljuvee.processor;

import info.kaljuvee.model.UserRecord;

public class NotesProcessor extends FieldProcessor {

    public NotesProcessor(String key) {
        super(key);
    }

    @Override
    public void parseInto(String value, UserRecord record) {
        validateInputs(value, record);
        record.setNotes(value);
    }

    @Override
    public String normalize(UserRecord record) {
        return record.getNotes();
    }
}
