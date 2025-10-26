package edu.dosw.sirha.common;

import org.springframework.stereotype.Component;

import edu.dosw.sirha.model.entity.Group;

@Component
public class AdvancedRestriction {

    public boolean groupCapacity(Group futureGroup) {
        return futureGroup.getAvailableQuotas() == 0 ? true : false;
    }
}
