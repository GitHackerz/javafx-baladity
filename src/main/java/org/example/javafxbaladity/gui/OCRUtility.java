package org.example.javafxbaladity.gui;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCRUtility {

    public static String extractTextFromImage(String imagePath) {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\21624\\Downloads\\Compressed\\tessdata-4.1.0");  // Set the tessdata path
        try {
            String extractedText = tesseract.doOCR(new File(imagePath));
            return extractedText;
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
            return "Error while reading image";
        }
    }

    public static String extractNumberFromText(String text) {
        // Updated regex to match exactly 8 digits
        String numberRegex = "\\b\\d{8}\\b";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(numberRegex);
        java.util.regex.Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }

        return "Number not found";
    }

    public static void main(String[] args) {
        String imagePath = "C://Users//user//IdeaProjects//demo6//src//main//resources//com//example//demo6//téléchargement.jpeg";  // Replace with the path to your image
        String text = OCRUtility.extractTextFromImage(imagePath);

        // Use the updated method to extract the number
        String number = OCRUtility.extractNumberFromText(text);
        System.out.println("Extracted Number: " + number);
    }
}
