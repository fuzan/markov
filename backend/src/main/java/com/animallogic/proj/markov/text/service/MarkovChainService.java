package com.animallogic.proj.markov.text.service;

import com.animallogic.proj.markov.text.entity.WordNode;
import com.animallogic.proj.markov.text.exception.ParameterInvalidException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.animallogic.proj.markov.text.util.PrefixUtil.getKey;
import static com.animallogic.proj.markov.text.util.PrefixUtil.removeLine;

@Service
@Slf4j
public class MarkovChainService {
    public final static String SPACE = " ";
    private final static Random RANDOM = new Random();
    private final Map<String, Map<String, WordNode>> cachedMap = new HashMap<>();

    public List<String> generateRandomNext(int numberOfGenerateWords, int keySize,
                                           List<String> content, String fileName) throws ParameterInvalidException {
        if (numberOfGenerateWords > 100000) {
            throw new ParameterInvalidException("Can only generate 100K words!");
        }

        String key = getKey(keySize, fileName);
        if (!cachedMap.containsKey(key)) {
            buildChainNodes(content, keySize, fileName);
        }

        Map<String, WordNode> map = cachedMap.get(key);
        List<String> res = new ArrayList<>();
        String prefix = getRandomKey(map);
        while (numberOfGenerateWords > 0) {
            PrefixAndPostFix prefixAndPostFix = searchNext(prefix, map);
            res.add(prefixAndPostFix.postfix);
            prefix = prefixAndPostFix.prefix;
            numberOfGenerateWords--;
        }
        return res;
    }

    public void buildChainNodes(List<String> content, int keySize, String fileName) throws ParameterInvalidException {
        if (keySize < 1) {
            throw new ParameterInvalidException("Key size must be larger than 0!");
        }
        if (content.size() < 20) {
            throw new ParameterInvalidException("Content must have more than 20 words !");
        }
        if (keySize > content.size() - 1) {
            throw new ParameterInvalidException("Key size is bigger than content length !");
        }

        Map<String, WordNode> map = new HashMap<>();

        for (int i = 0; i <= content.size() - keySize; i++) {
            List<String> list = content.subList(i, i + keySize);
            String key = String.join(SPACE, list);
            String value = (i + keySize < content.size()) ? content.get(i + keySize) : null;

            addNode(key.trim(), value, map);
        }

        cachedMap.put(getKey(keySize, fileName), map);
    }

    private void addNode(String prefix, String str, Map<String, WordNode> map) {
        if (map.containsKey(prefix)) {
            map.get(prefix).getPostfix().add(str);
        } else {
            WordNode markovNode = new WordNode(prefix);
            markovNode.setPostfix(new ArrayList<>());
            markovNode.getPostfix().add(str);
            map.put(prefix, markovNode);
        }
    }

    private PrefixAndPostFix searchNext(String prefix, Map<String, WordNode> map) {
        List<String> output = new ArrayList<>(Arrays.asList(prefix.split(SPACE)));
        WordNode node = map.get(prefix); // get all its suffix

        String postfix;
        if (node.getPostfix().size() == 1) {
            // the last prefix
            if (node.getPostfix().get(0) == null) {
                // assign first node to it
                node = map.values().stream().findFirst().get();
                return new PrefixAndPostFix(node.getStr(), node.getPostfix().get(0));
            } else {
                postfix = node.getPostfix().get(0);
                output.add(postfix);
            }
        } else {
            int next = RANDOM.nextInt(node.getPostfix().size());
            postfix = node.getPostfix().get(next);
            output.add(postfix);
        }
        String nextPrefix = output.stream().skip(1).limit(output.size()).reduce("", (o1, o2) -> o1 + SPACE + o2).trim();
        return new PrefixAndPostFix(nextPrefix, postfix);
    }

    private String getRandomKey(Map<String, WordNode> map) {
        int next = RANDOM.nextInt(map.values().size());
        List<WordNode> actualList = new ArrayList<>(map.values());
        return actualList.get(next).getStr();
    }

    @AllArgsConstructor
    static class PrefixAndPostFix {
        String prefix;
        String postfix;
    }
}
