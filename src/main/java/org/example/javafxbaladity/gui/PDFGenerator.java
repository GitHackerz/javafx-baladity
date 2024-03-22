package org.example.javafxbaladity.gui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.javafxbaladity.models.Document_request;

import java.io.FileOutputStream;

public class PDFGenerator {

    public static void GeneratePDF(Document_request d) {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("javafx-baladity-document\\src\\main\\resources\\org\\example\\javafxbaladity\\assets\\PDFGenerated.pdf"));
            doc.open();
            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Paragraph title = new Paragraph(d.getType_ddoc(), titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(title);
            // Add spacing
            doc.add(new Paragraph("\n"));
            // Create table for data fields
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            // Add fields with simple shaped box
            PdfPCell cell;
            Font fieldFont1 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
            Font fieldFont2 = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            cell = new PdfPCell(new Phrase("Note : \nLes utilisateurs sont tenus de respecter scrupuleusement la confidentialité des informations consignées dans le document, s'abstenant ainsi de les divulguer à des tiers sans l'autorisation expresse et préalable des autorités compétentes.\n Tout usage inapproprié, détourné ou en violation des dispositions clairement établies par le règlement en vigueur est formellement proscrit et peut entraîner des mesures disciplinaires sévères.\n En adhérant aux termes et conditions énoncés dans le règlement, les utilisateurs consentent et reconnaissent pleinement leur responsabilité quant à la préservation de l'intégrité, de la confidentialité et de la sécurité des informations contenues dans le document officiel, s'engageant ainsi à agir en conformité avec les principes éthiques et professionnels qui régissent son utilisation.", fieldFont1));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(10);
            table.addCell(cell);




            cell = new PdfPCell(new Phrase("Document Valable a partir du : "+d.getDate_ddoc(), fieldFont2));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(10);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Document Traité le : "+d.getDate_traitement_ddoc(), fieldFont2));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(10);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Données Personnels: "+d.getDescription_ddoc(), fieldFont2));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(10);
            table.addCell(cell);

            // Add more fields as needed



            // Add table to document
            doc.add(table);


            QRCodeGenerator.GenerateQRCode(d.getDescription_ddoc()+"\n ce document est valable a partir du "+d.getDate_ddoc()+"\nBaladity.tn");
            // Add image
            Image image = Image.getInstance("javafx-baladity-document\\src\\main\\resources\\org\\example\\javafxbaladity\\assets\\GeneratedQRcode.jpg");
            image.scaleToFit(400, 200);
            image.setAlignment(Element.ALIGN_CENTER);


            Image stamp = Image.getInstance("javafx-baladity-document\\src\\main\\resources\\org\\example\\javafxbaladity\\assets\\stamp.png");
            stamp.scaleToFit(100, 100);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);

            //stamp.setAlignment(Element.ALIGN_RIGHT);
            stamp.setAbsolutePosition(450,370);




            doc.add(image);
            doc.add(stamp);



            doc.close();
            System.out.println("PDF GENERATED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

