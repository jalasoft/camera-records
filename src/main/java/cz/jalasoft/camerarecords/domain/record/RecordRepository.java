package cz.jalasoft.camerarecords.domain.record;

import java.util.Collection;
import java.util.Optional;

public interface RecordRepository {

    Collection<VideoRecord> all();

    Optional<VideoRecord> byId(RecordId id);

    VideoRecord save(VideoRecord record);

    boolean deleteById(RecordId id);
}
