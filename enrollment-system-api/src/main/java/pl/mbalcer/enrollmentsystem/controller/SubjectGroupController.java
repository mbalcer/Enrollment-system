package pl.mbalcer.enrollmentsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectGroupDTO;
import pl.mbalcer.enrollmentsystem.model.enumeration.GroupType;
import pl.mbalcer.enrollmentsystem.service.SubjectGroupService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/subjectGroup")
@Slf4j
public class SubjectGroupController {
    private SubjectGroupService subjectGroupService;

    public SubjectGroupController(SubjectGroupService subjectGroupService) {
        this.subjectGroupService = subjectGroupService;
    }

    @GetMapping
    public ResponseEntity<List<SubjectGroupDTO>> getAllGroups() {
        List<SubjectGroupDTO> all = subjectGroupService.findAll();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectGroupDTO> getGroup(@PathVariable Long id) {
        Optional<SubjectGroupDTO> groupDTO = subjectGroupService.findOne(id);
        if (groupDTO.isPresent()) return ResponseEntity.ok().body(groupDTO.get());
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/byTeacher/{teacher}")
    public ResponseEntity<List<SubjectGroupDTO>> getAllGroupsByTeacher(@PathVariable String teacher) {
        List<SubjectGroupDTO> allByTeacher = subjectGroupService.findAllByTeacher(teacher);
        return ResponseEntity.ok(allByTeacher);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<SubjectGroupDTO>> getAllGroupsForRequests() {
        List<SubjectGroupDTO> allRequests = subjectGroupService.findAllByType(GroupType.INACTIVE);
        return ResponseEntity.ok(allRequests);
    }

    @GetMapping("/registration/{fieldOfStudyId}")
    public ResponseEntity<List<SubjectGroupDTO>> getAllGroupsForRegistration(@PathVariable Long fieldOfStudyId) {
        List<SubjectGroupDTO> allGroups = subjectGroupService.findAllRegistration(fieldOfStudyId);
        return ResponseEntity.ok(allGroups);
    }

    @PostMapping
    public ResponseEntity<SubjectGroupDTO> createGroup(@RequestBody SubjectGroupDTO groupDTO) throws URISyntaxException {
        SubjectGroupDTO result = subjectGroupService.create(groupDTO);
        return ResponseEntity.created(new URI("/api/subjectGroup/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectGroupDTO> updateGroup(@RequestBody SubjectGroupDTO groupDTO, @PathVariable Long id) throws URISyntaxException {
        SubjectGroupDTO result = subjectGroupService.update(groupDTO, id);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/add/student/{username}")
    public ResponseEntity<SubjectGroupDTO> addStudentToGroup(@RequestBody SubjectGroupDTO groupDTO, @PathVariable String username) {
        SubjectGroupDTO result = subjectGroupService.addStudentToGroup(groupDTO, username);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/remove/student/{username}")
    public ResponseEntity<SubjectGroupDTO> removeStudentFromGroup(@RequestBody SubjectGroupDTO groupDTO, @PathVariable String username) {
        SubjectGroupDTO result = subjectGroupService.removeStudentFromGroup(groupDTO, username);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        subjectGroupService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/type/{id}")
    public ResponseEntity<SubjectGroupDTO> updateTypeGroup(@RequestBody GroupType type, @PathVariable Long id) {
        SubjectGroupDTO result = subjectGroupService.updateGroupType(type, id);
        return ResponseEntity.ok().body(result);
    }

}
