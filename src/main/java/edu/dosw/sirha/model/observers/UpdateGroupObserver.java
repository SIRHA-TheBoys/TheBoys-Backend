package edu.dosw.sirha.model.observers;

import org.springframework.stereotype.Component;

@Component
public class UpdateGroupObserver implements GroupObserver{
    @Override
    public void updateAvailableCredits(String groupId, int oldQuotas, int actualQuotas){
        System.out.println("Grupo " + groupId + 
            " cambió de " + oldQuotas + " → " + actualQuotas + " cupos disponibles");

        if (actualQuotas == 0) {
            System.out.println("ALERTA!!! El grupo " + groupId + " ya no tiene cupos disponibles.");
        }
    }
}