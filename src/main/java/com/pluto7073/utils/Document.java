package com.pluto7073.utils;

import java.util.Arrays;
import java.util.Stack;

public class Document {

    private Stack<String> lines;

    public Document() {
        lines = new Stack<>();
    }

    public Document(String... lines) {
        this.lines = new Stack<>();
        this.lines.addAll(Arrays.asList(lines));
    }

    public Document(Stack<String> lines) {
        this.lines = lines;
    }

    public String getDocumentAsString() {
        StringBuilder builder = new StringBuilder();
        for (String s : lines) {
            builder.append(s).append("\n");
        }
        return builder.toString();
    }

    public String[] getLines() {
        String[] ls = new String[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            String s = lines.elementAt(i);
            ls[i] = s;
        }
        return ls;
    }

}
