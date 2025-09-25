

public class SubjectController{

    private final SubjectService subjectService;

    @PostMapping("")
    public ResponseEntity<SubjectResponseDTO> createSubject(
        @Valid @RequestBody SubjectRequestDTO dto)
    {
        SubjectResponseDTO created = subjectService.createSubject(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}