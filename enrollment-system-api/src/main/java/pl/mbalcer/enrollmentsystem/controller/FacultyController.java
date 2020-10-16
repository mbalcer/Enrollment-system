package pl.mbalcer.enrollmentsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbalcer.enrollmentsystem.model.dto.FacultyDTO;
import pl.mbalcer.enrollmentsystem.service.FacultyService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Slf4j
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/faculties")
    public ResponseEntity<List<FacultyDTO>> getAllFaculties() {
        log.debug("REST request to get a page of Faculties");
        List<FacultyDTO> all = facultyService.findAll();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/faculties/{id}")
    public ResponseEntity<FacultyDTO> getFaculty(@PathVariable Long id) {
        log.debug("REST request to get Faculty : {}", id);
        Optional<FacultyDTO> facultyDTO = facultyService.findOne(id);
        if (facultyDTO.isPresent()) return ResponseEntity.ok().body(facultyDTO.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/faculties")
    public ResponseEntity<FacultyDTO> createFaculty(@RequestBody FacultyDTO facultyDTO) throws URISyntaxException {
        log.debug("REST request to save Faculty : {}", facultyDTO);
        FacultyDTO result = facultyService.create(facultyDTO);
        return ResponseEntity.created(new URI("/api/faculties/" + result.getId())).body(result);
    }

    @PutMapping("/faculties/{id}")
    public ResponseEntity<FacultyDTO> updateFaculty(@RequestBody FacultyDTO facultyDTO, @PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Faculty : {}", facultyDTO);
        FacultyDTO result = facultyService.update(facultyDTO, id);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/faculties/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        log.debug("REST request to delete Faculty : {}", id);
        facultyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
