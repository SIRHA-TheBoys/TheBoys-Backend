package java.edu.dosw.sirha.service;

import java.edu.dosw.sirha.dto.StadisticResponseDTO;

public interface StatisticsService {

    StadisticResponseDTO calculateRequestApprovesDenied();

    StadisticResponseDTO mostRequestedGroups();

}
