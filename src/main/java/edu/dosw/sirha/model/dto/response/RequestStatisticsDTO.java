package edu.dosw.sirha.model.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatisticsDTO {

    private long totalRequests;

    private long approved;

    private long rejected;

    private double approvalRate;

    private double rejectionRate;

    private List<GroupCountDTO> mostRequestedGroups;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupCountDTO {
        private String groupId;
        private long count;
    }
}
