package pl.mbalcer.enrollmentsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbalcer.enrollmentsystem.model.dto.StudentDTO;
import pl.mbalcer.enrollmentsystem.service.StudentService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/student")
@Slf4j
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> all = studentService.findAll();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        Optional<StudentDTO> studentDTO = studentService.findOne(id);
        if (studentDTO.isPresent()) return ResponseEntity.ok().body(studentDTO.get());
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/byUsername/{username}")
    public ResponseEntity<StudentDTO> getStudentByUsername(@PathVariable String username) {
        StudentDTO studentDTO = studentService.getDTOByUsername(username);
        return ResponseEntity.ok(studentDTO);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) throws URISyntaxException {
        StudentDTO result = studentService.create(studentDTO);
        return ResponseEntity.created(new URI("/api/student/" + result.getUsername())).body(result);
    }

    @PutMapping("/{username}")
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentDTO, @PathVariable String username) {
        StudentDTO result = studentService.updateByUsername(studentDTO, username);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
