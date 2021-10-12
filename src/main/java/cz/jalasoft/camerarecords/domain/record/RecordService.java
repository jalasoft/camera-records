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
        return Flux.fromIterable(repository.all());
    }

    public Mono<VideoRecord> byId(RecordId id) {
        return repository.byId(id).map(Mono::just).orElse(Mono.empty());
    }

    public Mono<Void> saveRecord(VideoRecord record) {
        return Mono.fromCallable(() -> repository.save(record)).then();
    }

    public Mono<Void> deleteRecord(RecordId id) {
        return Mono.fromCallable(()->repository.deleteById(id)).then();
    }
}
