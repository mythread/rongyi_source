package com.fanxian.biz.common.tools;

import java.util.UUID;

import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;

public class HtmlUtil {

    public static String html2text(String html) {
        try {
            Parser parser = Parser.createParser(html, "utf-8");
            TextExtractingVisitor visitor = new TextExtractingVisitor();
            parser.visitAllNodesWith(visitor);
            String extractedText = visitor.getExtractedText();
            return extractedText;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        UUID randomUUID = UUID.randomUUID();
        System.out.println(randomUUID.toString());
        System.out.println(randomUUID.toString().length());
    }
}
