package java.edu.dosw.sirha.common;

import java.edu.dosw.sirha.model.Group;

import org.springframework.stereotype.Component;

@Component
public class AdvancedRestriction {
    public boolean groupCapacity(Group futureGroup) {
        return futureGroup.getAvailableQuotas() == 0 ? true : false;
    }
}
