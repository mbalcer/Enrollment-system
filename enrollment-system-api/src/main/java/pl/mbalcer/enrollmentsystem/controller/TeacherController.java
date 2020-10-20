package pl.mbalcer.enrollmentsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbalcer.enrollmentsystem.model.dto.TeacherDTO;
import pl.mbalcer.enrollmentsystem.service.TeacherService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/teacher")
@Slf4j
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        log.debug("REST request to get a page of Teachers");
        List<TeacherDTO> all = teacherService.findAll();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacher(@PathVariable Long id) {
        log.debug("REST request to get Teacher : {}", id);
        Optional<TeacherDTO> teacherDTO = teacherService.findOne(id);
        if (teacherDTO.isPresent()) return ResponseEntity.ok().body(teacherDTO.get());
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/byUsername/{username}")
    public ResponseEntity<TeacherDTO> getTeacherByUsername(@PathVariable String username) {
        log.debug("REST request to get Teacher by username : {}", username);
        TeacherDTO teacherDTO = teacherService.findOneByUsername(username);
        return ResponseEntity.ok(teacherDTO);
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacherDTO) throws URISyntaxException {
        log.debug("REST request to save Teacher : {}", teacherDTO);
        TeacherDTO result = teacherService.create(teacherDTO);
        return ResponseEntity.created(new URI("/api/teacher/" + result.getUsername())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@RequestBody TeacherDTO teacherDTO, @PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Teacher : {}", teacherDTO);
        TeacherDTO result = teacherService.update(teacherDTO, id);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        log.debug("REST request to delete Teacher : {}", id);
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
