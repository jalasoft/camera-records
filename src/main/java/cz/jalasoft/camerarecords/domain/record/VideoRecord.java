package cz.jalasoft.camerarecords.domain.record;

import lombok.Builder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Builder(builderMethodName = "newRecord", toBuilder = true)
public final class VideoRecord {

    private RecordId id;
    private LocalDateTime createdAt;
    private String filename;
    private Duration duration;
    private Set<Tag> tags;

    public RecordId id() {
        return id;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public String filename() {
        return filename;
    }

    public Duration duration() {
        return duration;
    }

    public Set<Tag> tags() {
        return tags;
    }
}
