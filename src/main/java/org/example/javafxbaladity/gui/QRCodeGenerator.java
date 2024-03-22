package org.example.javafxbaladity.gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.Paths;


// first add dependencies in pom.xml --> 2 dependencies
// second add all required imports



public class QRCodeGenerator {
    public static void GenerateQRCode(String data) throws WriterException, IOException {
        String path="C:\\Users\\21624\\Desktop\\Integration\\javafx-baladity-document\\src\\main\\resources\\org\\example\\javafxbaladity\\assets\\GeneratedQRcode.jpg";
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE,500,500);
        MatrixToImageWriter.writeToPath(matrix, "jpg",Paths.get(path));
        System.out.println("QR CODE SUCC CREATED");
    }

}
