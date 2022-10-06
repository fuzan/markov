package com.animallogic.proj.markov.text.api;

import com.animallogic.proj.markov.text.exception.ParameterInvalidException;
import com.animallogic.proj.markov.text.service.FileService;
import com.animallogic.proj.markov.text.service.MarkovChainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class WordGeneratorController {

    private final int DEFAULT_KEY_SIZE = 2;

    @Autowired
    private FileService fileService;

    @Autowired
    private MarkovChainService service;

    @CrossOrigin
    @GetMapping("/api/generate")
    @ResponseBody
    public ResponseEntity<String> generateText(@RequestParam int keySize,
                                               @RequestParam int numberOfWords,
                                               @RequestParam String fileName) throws ParameterInvalidException {
        if (!fileService.contentLoad) {
            return ResponseEntity.ok("Please upload a source file first !");
        }

        List<String> output = service.generateRandomNext(numberOfWords, keySize, fileService.getProcessedContent(), fileName);
        return ResponseEntity.ok(String.join(" ", output));
    }

    @CrossOrigin
    @PostMapping("/api/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {
        try {
            fileService.storeFile(file);
            service.buildChainNodes(fileService.getProcessedContent(), DEFAULT_KEY_SIZE, file.getOriginalFilename());
        } catch (ParameterInvalidException | IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.ok("upload success!");
    }
}
