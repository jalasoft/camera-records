package cz.jalasoft.camerarecords.domain.record;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository repository;

    public Flux<VideoRecord> allRecords() {
        return repository.all();
    }

    public Mono<Void> saveRecord(VideoRecord record) {
        return repository.save(record).then();
    }

    public Mono<Void> deleteRecord(RecordId id) {
        return repository.deleteById(id);
    }
}
