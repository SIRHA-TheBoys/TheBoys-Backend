package edu.dosw.sirha.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class RequestController{

    private final RequestService requestService;

    @PostMapping("")
    public ResponseEntity<RequestResponseDTO> createRequest(
        @Valid @RequestBody RequestDTO dto)
    {
        RequestResponseDTO created = requestService.createRequest(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/requests/{id}")
    public ResponseEntity<RequestResponseDTO> updateRequest(
        @Parameter(description = "Request to be updated", required = true)
        @PathVariable ObjectId id,
        @Valid @RequestBody RequestDTO dto)
    {
        RequestResponseDTO requestUpdated = requestService.updateRequest(ObjectId id, RequestDTO dto);

        return ResponseEntity.ok(requestUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(
        @Parameter(description = "Request to be deleted", required = true)
        @PathVariable ObjectId id) {
            requestService.deleteRequest(id);
        }
}