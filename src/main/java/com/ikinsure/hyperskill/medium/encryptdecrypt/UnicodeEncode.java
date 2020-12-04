package com.ikinsure.hyperskill.medium.encryptdecrypt;

public class UnicodeEncode implements EncodeInterface{
    @Override
    public String encode(String t, int k) {
        return t.chars().map(c -> c + k)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}