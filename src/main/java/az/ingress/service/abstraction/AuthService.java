package az.ingress.service.abstraction;

public interface AuthService {
    boolean verify(String token);
}