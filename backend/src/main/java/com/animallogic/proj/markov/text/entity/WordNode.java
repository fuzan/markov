package com.animallogic.proj.markov.text.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WordNode {
    String str;
    List<String> postfix;

    public WordNode(String str) {
        this.str = str;
    }
}

