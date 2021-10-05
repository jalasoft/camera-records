package cz.jalasoft.camerarecords.controller.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class VideoRecordResource {

    private long id;
    private String date;
    private long durationSeconds;
    private String name;
    private Set<String> tags;

    @JsonCreator
    public VideoRecordResource(
            @JsonProperty("id")
            long id,
            @JsonProperty("data")
            String date,
            @JsonProperty("duration_seconds")
            long durationSeconds,
            @JsonProperty("name")
            String name,
            @JsonProperty("tags")
            Set<String> tags) {
        this.id = id;
        this.date = date;
        this.durationSeconds = durationSeconds;
        this.name = name;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    @JsonProperty("duration_seconds")
    public long getDurationSeconds() {
        return durationSeconds;
    }

    public String getName() {
        return name;
    }

    public Set<String> getTags() {
        return tags;
    }
}
