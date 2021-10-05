package cz.jalasoft.camerarecords.controller;

import cz.jalasoft.camerarecords.controller.resources.VideoRecordResource;
import cz.jalasoft.camerarecords.domain.record.RecordId;
import cz.jalasoft.camerarecords.domain.record.RecordService;
import cz.jalasoft.camerarecords.domain.record.Tag;
import cz.jalasoft.camerarecords.domain.record.VideoRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordsController {

    private final RecordService service;

    @GetMapping("/")
    public Flux<VideoRecordResource> allRecords() {
        return service.allRecords().map(this::resource);
    }

    private VideoRecordResource resource(VideoRecord record) {
        return new VideoRecordResource(
                record.id().value(),
                record.createdAt().toString(),
                record.duration().getSeconds(),
                record.filename(),
                record.tags().stream().map(Tag::value).collect(Collectors.toSet())
        );
    }

    @PostMapping("/")
    public Mono<ResponseEntity<Void>> addRecord(@RequestBody Mono<VideoRecordResource> resource) {
        return resource.flatMap(r -> service.saveRecord(record(r))).then(Mono.just(ResponseEntity.ok().build()));
    }

    private VideoRecord record(VideoRecordResource resource) {
        return VideoRecord.newRecord()
                .createdAt(LocalDateTime.parse(resource.getDate()))
                .duration(Duration.ofSeconds(resource.getDurationSeconds()))
                .filename(resource.getName())
                .tags(resource.getTags().stream().map(Tag::new).collect(Collectors.toSet()))
                .build();
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteRecord(@PathVariable("id") long id) {
        return service.deleteRecord(new RecordId(id)).then(Mono.just(ResponseEntity.ok().build()));
    }
}
