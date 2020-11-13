package de.stoxygen;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {
    private String currentAuditor = "admin";

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(currentAuditor);
    }


    public void setCurrentAuditor(String currentAuditor) {
        this.currentAuditor = currentAuditor;
    }
}
