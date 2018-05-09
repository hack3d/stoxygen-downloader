package de.stoxygen;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {
    private String currentAuditor = "admin";

    @Override
    public String getCurrentAuditor() {
        return currentAuditor;
    }


    public void setCurrentAuditor(String currentAuditor) {
        this.currentAuditor = currentAuditor;
    }
}
