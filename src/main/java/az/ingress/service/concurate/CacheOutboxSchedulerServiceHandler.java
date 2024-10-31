package az.ingress.service.concurate;

import az.ingress.dao.repository.RatingCacheOutboxRepository;
import az.ingress.service.abstraction.CacheOutboxSchedulerService;
import az.ingress.service.abstraction.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CacheOutboxSchedulerServiceHandler implements CacheOutboxSchedulerService {
    private final RatingCacheOutboxRepository ratingCacheOutboxRepository;
    private final CacheService cacheService;

    @Override
    public void processOutboxEntries() {
        var unprocessedEntries = ratingCacheOutboxRepository.findByProcessedFalse();
        unprocessedEntries.forEach(
                entry -> {
                    try {
                        cacheService.save(entry.getProductId(), entry.getVoteCount(), entry.getAverageRating());
                        entry.setProcessed(true);
                        ratingCacheOutboxRepository.save(entry);

                        log.info("Outbox entry successfully processed and marked. ID: {}", entry.getId());
                    } catch (Exception e) {
                        log.error("Error occurred while processing outbox entry. ID: {} - Will retry.", entry.getId(), e);
                    }
                }
        );
    }
}
