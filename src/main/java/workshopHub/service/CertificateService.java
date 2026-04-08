package workshopHub.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CertificateService {

    public byte[] generateCertificate(String userName, String workshopTitle) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 36);
                contentStream.newLineAtOffset(100, 700);
                contentStream.setNonStrokingColor(79, 70, 229);
                contentStream.showText("Certificate of Completion");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 20);
                contentStream.setNonStrokingColor(100, 100, 100);
                contentStream.newLineAtOffset(100, 600);
                contentStream.showText("This is to certify that");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 28);
                contentStream.setNonStrokingColor(0, 0, 0);
                contentStream.newLineAtOffset(100, 540);
                contentStream.showText(userName == null ? "User" : userName);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 20);
                contentStream.setNonStrokingColor(100, 100, 100);
                contentStream.newLineAtOffset(100, 480);
                contentStream.showText("has successfully completed the workshop");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 24);
                contentStream.setNonStrokingColor(16, 185, 129);
                contentStream.newLineAtOffset(100, 420);
                contentStream.showText(workshopTitle == null ? "Workshop" : workshopTitle);
                contentStream.endText();

                String date = new SimpleDateFormat("MMMM dd, yyyy").format(new Date());
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 16);
                contentStream.setNonStrokingColor(50, 50, 50);
                contentStream.newLineAtOffset(100, 300);
                contentStream.showText("Awarded on: " + date);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                contentStream.setNonStrokingColor(50, 50, 50);
                contentStream.newLineAtOffset(400, 300);
                contentStream.showText("WorkshopHub Platform");
                contentStream.endText();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        }
    }
}
