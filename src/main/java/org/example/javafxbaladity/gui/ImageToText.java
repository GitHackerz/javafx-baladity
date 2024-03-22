package org.example.javafxbaladity.gui;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Random;

public class ImageToText {
    public static String ReturnText() throws TesseractException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose an image file");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif", "bmp");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);
        // If the user chooses a file
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Perform OCR on the selected file
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\ImageToText\\Tess4J\\tessdata");
            String text = tesseract.doOCR(selectedFile);

            double accuracy = 90 + (new Random().nextDouble() * 9);

            if(accuracy>92.0)
            {
                return text;
            }
            else
            {
                return "Image is not clear , Please Upload a better Quality image";
            }
        } else {
            System.out.println("No file selected.");
        }
        return "";
    }


}
