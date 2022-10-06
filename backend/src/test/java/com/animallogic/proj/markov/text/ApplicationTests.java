package com.animallogic.proj.markov.text;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    @DisplayName("Should successfully upload file")
    void testValidFileUpload() {
        MultiValueMap<String, FileSystemResource> map = new LinkedMultiValueMap<>();
        map.add("file", tmpFile());
        ResponseEntity<String> res = this.template.postForEntity("http://localhost:" + port + "/api/upload",
                new HttpEntity<>(map, headers()),
                String.class);

        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should fail upload file")
    void testSmallFileUpload() {
        MultiValueMap<String, FileSystemResource> map = new LinkedMultiValueMap<>();
        map.add("file", smallFile());
        ResponseEntity<String> res = this.template.postForEntity("http://localhost:" + port + "/api/upload",
                new HttpEntity<>(map, headers()),
                String.class);

        assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private HttpHeaders headers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        return httpHeaders;
    }

    private FileSystemResource tmpFile() {
        return new FileSystemResource(Path.of("src", "test", "resources", "modi.txt"));
    }

    private FileSystemResource smallFile() {
        return new FileSystemResource(Path.of("src", "test", "resources", "small_modi.txt"));
    }

}
