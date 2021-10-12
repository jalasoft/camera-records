package cz.jalasoft.camerarecords.infrastructure;

import cz.jalasoft.camerarecords.domain.record.RecordId;
import cz.jalasoft.camerarecords.domain.record.RecordRepository;
import cz.jalasoft.camerarecords.domain.record.Tag;
import cz.jalasoft.camerarecords.domain.record.VideoRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static java.time.Duration.ofSeconds;
import static java.util.Set.of;

@Repository
public class InMemoryRecordRepository implements RecordRepository {

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

    private final Collection<VideoRecord> records;

    public InMemoryRecordRepository() {
        this.records = new ArrayList<>();
        this.records.addAll(DATA);
    }

    @Cacheable(value = "recordz")
    @Override
    public Collection<VideoRecord> all() {
        return new ArrayList<>(records);
    }

    @Cacheable(value = "recordz")
    @Override
    public Optional<VideoRecord> byId(RecordId id) {
        return records.stream().filter(r -> r.id().equals(id)).findFirst();
    }

    @CachePut(value="recordz", key = "#record.id()")
    @Override
    public VideoRecord save(VideoRecord record) {
            var newRecord = record.toBuilder().id(new RecordId(idCounter++)).build();
            records.add(newRecord);
            return newRecord;
    }

    @CacheEvict(value="recordz", key="#id")
    @Override
    public boolean deleteById(RecordId id) {
            VideoRecord found = null;
            for(var record : records) {
                if (record.id().equals(id)) {
                    found = record;
                }
            }

            if (found == null) {
                return false;
            }
            return records.remove(found);
    }
}
