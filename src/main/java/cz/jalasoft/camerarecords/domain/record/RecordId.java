package cz.jalasoft.camerarecords.domain.record;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class RecordId {

    private final long value;

    public RecordId(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }
}
