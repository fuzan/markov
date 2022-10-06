package com.animallogic.proj.markov.text.service;

import com.animallogic.proj.markov.text.exception.ParameterInvalidException;
import lombok.Getter;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.animallogic.proj.markov.text.service.MarkovChainService.SPACE;
import static com.animallogic.proj.markov.text.util.PrefixUtil.removeLine;

@Service
@Getter
public class FileService {
    private final Tika tika = new Tika();

    public boolean contentLoad;

    private final List<String> processedContent = new ArrayList<>();

    public void storeFile(MultipartFile file) throws ParameterInvalidException, IOException {
        if (file == null || file.isEmpty()) {
            throw new ParameterInvalidException("File is empty!");
        }

        if (!tika.detect(file.getInputStream()).equals("text/plain")) {
            throw new ParameterInvalidException("File must be plain text file, can not be media, pictures!");
        }
        processedContent.clear();
        processedContent.addAll(processFile(file));
    }

    private List<String> processFile(MultipartFile file) throws IOException {
        final List<String> content = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = reader.readLine();
            while (line != null) {
                content.add(removeLine(line.trim()));
                line = reader.readLine();
            }
        }
        contentLoad = true;
        return loadContent(content);
    }


    /**
     * @return split each word from line and saved in a List structure.
     */
    private List<String> loadContent(List<String> content) {
        List<String> res = new ArrayList<>();
        content.stream().filter(s -> !s.trim().equals(""))
                .forEach(c -> Arrays.stream(c.split(SPACE))
                        .forEach(ea -> res.add(ea.trim())));
        return res;
    }
}
