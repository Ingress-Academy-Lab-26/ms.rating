package az.ingress.service.abstraction;

public interface CacheOutboxSchedulerService {
    void processOutboxEntries();
}
