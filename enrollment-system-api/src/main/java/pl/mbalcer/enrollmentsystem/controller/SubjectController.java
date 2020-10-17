package pl.mbalcer.enrollmentsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectDTO;
import pl.mbalcer.enrollmentsystem.service.SubjectService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/subject")
@Slf4j
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        log.debug("REST request to get a page of Subjects");
        List<SubjectDTO> all = subjectService.findAll();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getFaculty(@PathVariable Long id) {
        log.debug("REST request to get Subject : {}", id);
        Optional<SubjectDTO> subjectDTO = subjectService.findOne(id);
        if (subjectDTO.isPresent()) return ResponseEntity.ok().body(subjectDTO.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SubjectDTO> createFaculty(@RequestBody SubjectDTO subjectDTO) throws URISyntaxException {
        log.debug("REST request to save Subject : {}", subjectDTO);
        SubjectDTO result = subjectService.create(subjectDTO);
        return ResponseEntity.created(new URI("/api/subject/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> updateFaculty(@RequestBody SubjectDTO subjectDTO, @PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Subject : {}", subjectDTO);
        SubjectDTO result = subjectService.update(subjectDTO, id);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        log.debug("REST request to delete Subject : {}", id);
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
