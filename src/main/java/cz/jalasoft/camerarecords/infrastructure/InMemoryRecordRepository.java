package cz.jalasoft.camerarecords.infrastructure;

import cz.jalasoft.camerarecords.domain.record.VideoRecord;
import cz.jalasoft.camerarecords.domain.record.RecordId;
import cz.jalasoft.camerarecords.domain.record.RecordRepository;
import cz.jalasoft.camerarecords.domain.record.Tag;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.time.Duration.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static java.util.Set.*;

@Repository
public final class InMemoryRecordRepository implements RecordRepository {

    private static long idCounter = 0;

    private static final Collection<VideoRecord> DATA = Set.of(
            VideoRecord.newRecord()
                    .id(new RecordId(idCounter++))
                    .createdAt(LocalDateTime.parse("2021-01-13T17:09:42.411"))
                    .duration(ofSeconds(66))
                    .filename("IMG_0001.MOV")
                    .tags(of(new Tag("Lukasek"), new Tag("Natalka")))
                    .build(),
            VideoRecord.newRecord()
                    .id(new RecordId(idCounter++))
                    .createdAt(LocalDateTime.parse("2021-07-02T10:00:42.000"))
                    .duration(ofSeconds(45))
                    .filename("IMG_0002.MOV")
                    .tags(of(new Tag("Lukasek"), new Tag("Chata")))
                    .build(),
            VideoRecord.newRecord()
                    .id(new RecordId(idCounter++))
                    .createdAt(LocalDateTime.parse("2021-08-13T18:00:12.411"))
                    .duration(ofSeconds(22))
                    .filename("IMG_0003.MOV")
                    .tags(of(new Tag("Krkonose"), new Tag("Natalka")))
                    .build()
    );

    public static void main(String[] args) {
        var h = LocalDateTime.parse("2021-08-13T18:00:12.411");
        System.out.println(h);
    }

    private final Collection<VideoRecord> records;

    public InMemoryRecordRepository() {
        this.records = new ArrayList<>();
        this.records.addAll(DATA);
    }

    @Override
    public Flux<VideoRecord> all() {
        return Flux.fromIterable(records);
    }

    @Override
    public Mono<VideoRecord> save(VideoRecord record) {
        return Mono.fromCallable(()-> {
            var newRecord = record.toBuilder().id(new RecordId(idCounter++)).build();
            records.add(newRecord);
            return newRecord;
        });
    }

    @Override
    public Mono<Void> deleteById(RecordId id) {

        return Mono.fromCallable(() -> {
            VideoRecord found = null;
            for(var record : records) {
                if (record.id().equals(id)) {
                    found = record;
                }
            }

            if (found == null) {
                return Mono.error(new RuntimeException("No record with id " + id + " found."));
            }
            records.remove(found);
            return null;
        }).then();
    }
}
