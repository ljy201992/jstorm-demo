package com.ljy.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Desc
 * @Author lijianying@youku.com
 * @Created-Time 8/16/16-5:09 PM.
 */
public final class WordGenerator {

    private static final char[] BASIC_CHARS = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q'};
    private static final int WORD_LENGTH = 3;
    private static final int LIST_SIZE = 100;

    public static final List<String> generateList(){
        List<String> result = new ArrayList<>(LIST_SIZE);
        for (int i = 0; i < LIST_SIZE; i++) {
            char[] charArr = new char[WORD_LENGTH];
            for (int j = 0; j < WORD_LENGTH; j++) {
                charArr[j] = BASIC_CHARS[(int)(Math.random()*BASIC_CHARS.length)];
            }
            result.add(new String(charArr));
        }

        return result;
    }

    public static final String generateString(){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < LIST_SIZE; i++) {
            char[] charArr = new char[WORD_LENGTH];
            for (int j = 0; j < WORD_LENGTH; j++) {
                charArr[j] = BASIC_CHARS[(int)(Math.random()*BASIC_CHARS.length)];
            }
            buffer.append(new String(charArr)).append(",");
        }

        return buffer.deleteCharAt(buffer.length()-1).toString();
    }
}
