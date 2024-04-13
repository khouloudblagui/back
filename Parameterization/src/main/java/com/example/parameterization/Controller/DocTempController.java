package com.example.parameterization.Controller;

import com.example.parameterization.Entity.DocTemp;
import com.example.parameterization.Enum.ElementType;
import com.example.parameterization.Enum.TemplateFormat;
import com.example.parameterization.Repository.DocTempRepo;
import com.example.parameterization.Service.DocTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/docTemp")
public class DocTempController {
    private DocTempRepo documentTemplateRepository;
    @Autowired
    private DocTempService docTempService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile ifile,
                                             @RequestParam("title") String ititle,
                                             @RequestParam("description") String idescription,
                                             @RequestParam("format") TemplateFormat iformat,
                                             @RequestParam("parentElementType") ElementType iparentElementType,
                                             @RequestParam("parentElementKey") int iparentElementKey) {
        try {
            docTempService.uploadDocument(ifile, ititle, idescription, iformat, iparentElementType, iparentElementKey);
            return ResponseEntity.status(HttpStatus.OK).body("Document uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload document.");
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<HttpStatus> deleteDocTemp(@PathVariable("id") Long iIdDocTemp) {
        Optional<DocTemp> aExistingDocTemp = Optional.ofNullable(docTempService.getDocumentById(iIdDocTemp));
        if (aExistingDocTemp.isPresent()) {
            docTempService.deleteDocument(iIdDocTemp);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDocument(@PathVariable Long id,
                                                 @RequestParam("title") String ititle,
                                                 @RequestParam("description") String idescription,
                                                 @RequestParam("format") TemplateFormat iformat,
                                                 @RequestParam("parentElementType") ElementType iparentElementType,
                                                 @RequestParam("parentElementKey") int iparentElementKey) {
        try {
            docTempService.updateDocument(id, ititle, idescription, iformat, iparentElementType, iparentElementKey);
            return ResponseEntity.status(HttpStatus.OK).body("Document updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update document.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DocTemp>> getAllDocuments() {
        List<DocTemp> aDocuments = docTempService.getAllDocuments();
        return ResponseEntity.status(HttpStatus.OK).body(aDocuments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocTemp> getDocumentById(@PathVariable Long id) {
        DocTemp aDocuments = docTempService.getDocumentById(id);
        if (aDocuments != null) {
            return ResponseEntity.status(HttpStatus.OK).body(aDocuments);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        DocTemp docTemp = docTempService.getDocumentById(id);
        if (docTemp != null) {
            byte[] documentData = docTemp.getFileData();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            // Set filename with the title instead of a fixed name
            String filename = docTemp.getTemplateDocTitle().replaceAll("\\s+", "_"); // Replace spaces with underscores
            headers.setContentDispositionFormData("attachment", filename + "." + docTemp.getTemplateDoc_Format().toString().toLowerCase());

            return new ResponseEntity<>(documentData, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/checkTitleExists")
    public ResponseEntity<Boolean> checkTitleExists(@RequestParam("title") String title) {
        boolean titleExists = docTempService.checkTitleExists(title);
        return ResponseEntity.ok(titleExists);
    }
}
