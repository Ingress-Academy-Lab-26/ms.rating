package az.ingress.schedular;

import az.ingress.service.abstraction.CacheOutboxSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RatingCacheOutboxScheduler {
    private final CacheOutboxSchedulerService cacheOutboxSchedulerService;


    @Scheduled(cron = "5 * * * * * ")
    @SchedulerLock(name = "processOutboxEntries", lockAtLeastFor = "PT1M", lockAtMostFor = "PT3M")
    public void processOutboxEntries() {
        cacheOutboxSchedulerService.processOutboxEntries();
    }
}
