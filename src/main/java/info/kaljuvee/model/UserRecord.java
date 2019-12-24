package info.kaljuvee.model;

import java.time.ZonedDateTime;
import java.util.StringJoiner;

public class UserRecord {

    private ZonedDateTime timestamp;
    private String address;
    private String zip;
    private String fullName;
    private Double fooDuration;
    private Double barDuration;
    private Double totalDuration;
    private String notes;

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Double getFooDuration() {
        return fooDuration;
    }

    public void setFooDuration(Double fooDuration) {
        this.fooDuration = fooDuration;
    }

    public Double getBarDuration() {
        return barDuration;
    }

    public void setBarDuration(Double barDuration) {
        this.barDuration = barDuration;
    }

    public Double getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Double totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserRecord.class.getSimpleName() + "[", "]")
                .add("timestamp=" + timestamp)
                .add("address='" + address + "'")
                .add("zip='" + zip + "'")
                .add("fullName='" + fullName + "'")
                .add("fooDuration=" + fooDuration)
                .add("barDuration=" + barDuration)
                .add("totalDuration=" + totalDuration)
                .add("notes='" + notes + "'")
                .toString();
    }
}
