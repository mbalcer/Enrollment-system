package pl.mbalcer.enrollmentsystem.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectGroupDTO;
import pl.mbalcer.enrollmentsystem.service.PdfService;
import pl.mbalcer.enrollmentsystem.service.SubjectGroupService;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin
public class PdfController {
    private PdfService pdfService;
    private SubjectGroupService groupService;

    public PdfController(PdfService pdfService, SubjectGroupService groupService) {
        this.pdfService = pdfService;
        this.groupService = groupService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> downloadPdfGroup(@PathVariable Long id) {
        SubjectGroupDTO groupDTO = groupService.findOne(id).get();
        InputStreamResource resource = pdfService.getPdfStream(groupDTO);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=group.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
