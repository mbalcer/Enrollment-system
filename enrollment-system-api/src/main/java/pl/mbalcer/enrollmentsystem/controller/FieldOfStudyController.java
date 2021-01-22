package pl.mbalcer.enrollmentsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbalcer.enrollmentsystem.model.dto.FieldOfStudyDTO;
import pl.mbalcer.enrollmentsystem.service.FieldOfStudyService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/fieldOfStudy")
@Slf4j
public class FieldOfStudyController {
    private FieldOfStudyService fieldOfStudyService;

    public FieldOfStudyController(FieldOfStudyService fieldOfStudyService) {
        this.fieldOfStudyService = fieldOfStudyService;
    }

    @GetMapping
    public ResponseEntity<List<FieldOfStudyDTO>> getAllFieldsOfStudy() {
        List<FieldOfStudyDTO> all = fieldOfStudyService.findAll();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldOfStudyDTO> getFieldOfStudy(@PathVariable Long id) {
        Optional<FieldOfStudyDTO> fieldOfStudyDTO = fieldOfStudyService.findOne(id);
        if (fieldOfStudyDTO.isPresent()) return ResponseEntity.ok().body(fieldOfStudyDTO.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<FieldOfStudyDTO> createFieldOfStudy(@RequestBody FieldOfStudyDTO fieldOfStudyDTO) throws URISyntaxException {
        FieldOfStudyDTO result = fieldOfStudyService.create(fieldOfStudyDTO);
        return ResponseEntity.created(new URI("/api/fieldOfStudy/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldOfStudyDTO> updateFieldOfStudy(@RequestBody FieldOfStudyDTO fieldOfStudyDTO, @PathVariable Long id) throws URISyntaxException {
        FieldOfStudyDTO result = fieldOfStudyService.update(fieldOfStudyDTO, id);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFieldOfStudy(@PathVariable Long id) {
        fieldOfStudyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
