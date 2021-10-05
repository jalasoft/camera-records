package cz.jalasoft.camerarecords.domain.record;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecordRepository {

    Flux<VideoRecord> all();

    Mono<VideoRecord> save(VideoRecord record);

    Mono<Void> deleteById(RecordId id);
}
