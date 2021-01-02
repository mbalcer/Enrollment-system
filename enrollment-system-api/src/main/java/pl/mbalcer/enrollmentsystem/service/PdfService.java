package pl.mbalcer.enrollmentsystem.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.model.dto.StudentDTO;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectGroupDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

@Service
public class PdfService {
    public InputStreamResource getPdfStream(SubjectGroupDTO group) {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 24, BaseColor.BLACK);
            Paragraph chunk = new Paragraph("Group no. " + group.getId(), font);
            chunk.setAlignment(Element.ALIGN_CENTER);
            document.add(chunk);
            document.add(new Paragraph("Subject: " + group.getSubjectDTO().getName()));
            document.add(new Paragraph("Teacher: " + group.getNameTeacher()));
            if (group.getPlace() != null)
                document.add(new Paragraph("Place: " + group.getPlace()));
            if (group.getCourseTime() != null)
                document.add(new Paragraph("Course time: " + group.getCourseTime()));
            document.add(new Paragraph("Max limit: " + group.getNumberOfPlaces()));
            document.add(new Paragraph("Fields of study: "));
            List fieldsOfStudy = new List(List.UNORDERED);
            group.getFieldsOfStudyDTO().forEach(fieldOfStudy -> {
                fieldsOfStudy.add(new ListItem(fieldOfStudy.getName() + ", " + fieldOfStudy.getType() + ", " + fieldOfStudy.getMode()));
            });
            document.add(fieldsOfStudy);

            if (group.getTimeTableDTO().size() > 0) {
                document.add(new Paragraph("Time table: "));
                List timeTable = new List(List.ORDERED);
                group.getTimeTableDTO()
                     .forEach(appointment -> timeTable.add(new ListItem(
                             appointment.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                                     " - " + appointment.getEndTime().format(DateTimeFormatter.ISO_LOCAL_TIME))));
                document.add(timeTable);
            }
            if (group.getStudentsDTO().size() > 0) {
                document.add(Chunk.NEWLINE);
                int timeTableSize = group.getTimeTableDTO().size();
                PdfPTable table = new PdfPTable(timeTableSize + 1);
                setWidthTable(timeTableSize, table);
                addTableHeader(table, timeTableSize);
                addRows(table, group.getStudentsDTO(), timeTableSize);
                document.add(table);
            }
            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        ByteArrayOutputStream stream = baos;
        InputStream input = new ByteArrayInputStream(stream.toByteArray());
        return new InputStreamResource(input);
    }

    private void setWidthTable(int timeTableSize, PdfPTable table) throws DocumentException {
        float[] widths = new float[timeTableSize + 1];
        widths[0] = 2;
        for (int i = 1; i < widths.length; i++) {
            widths[i] = 0.5f;
        }
        table.setWidths(widths);
    }

    private void addRows(PdfPTable table, java.util.List<StudentDTO> studentsDTO, Integer timeTableSize) {
        studentsDTO.forEach(student -> {
            table.addCell(student.getFullName());
            IntStream.rangeClosed(1, timeTableSize).forEach(a -> table.addCell("."));
        });
    }

    private void addTableHeader(PdfPTable table, Integer timeTableSize) {
        PdfPCell studentColumn = new PdfPCell();
        studentColumn.setBackgroundColor(BaseColor.LIGHT_GRAY);
        studentColumn.setBorderWidth(1);
        studentColumn.setPhrase(new Phrase("Student"));
        table.addCell(studentColumn);

        IntStream.rangeClosed(1, timeTableSize)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(String.valueOf(columnTitle)));
                    table.addCell(header);
                });
    }
}
