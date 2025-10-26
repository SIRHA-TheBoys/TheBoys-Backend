package edu.dosw.sirha.model.observers;

import org.springframework.stereotype.Component;

@Component
public interface GroupObserver {
    public void updateAvailableCredits(String groupId, int oldQuotas, int actualQuotas);
}
