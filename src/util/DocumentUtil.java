package util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.RekamMedis;

public class DocumentUtil {

    // Generate QR Code as iText Image
    private static Image getQRCodeImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray(); 
            
            return Image.getInstance(pngData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean exportSuratSakit(RekamMedis rm, String destPath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(destPath));
            document.open();

            // Font definitions
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
            Font subTitleFont = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.DARK_GRAY);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);

            // 1. Header / Kop Surat
            Paragraph clinicName = new Paragraph("SMART CLINIC", titleFont);
            clinicName.setAlignment(Element.ALIGN_CENTER);
            document.add(clinicName);

            Paragraph clinicAddress = new Paragraph("Jl. Raya Kesehatan No. 123, Kota Sehat, 12345\nTelp: (021) 555-1234 | Email: info@smartclinic.com", subTitleFont);
            clinicAddress.setAlignment(Element.ALIGN_CENTER);
            document.add(clinicAddress);

            document.add(new Chunk(new LineSeparator(1f, 100f, BaseColor.BLACK, Element.ALIGN_CENTER, -1f)));
            document.add(new Paragraph("\n"));

            // 2. Title
            Paragraph title = new Paragraph("SURAT KETERANGAN SAKIT", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD | Font.UNDERLINE, BaseColor.BLACK));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // 3. Opening Text
            document.add(new Paragraph("Yang bertanda tangan di bawah ini, menerangkan bahwa:", normalFont));
            document.add(new Paragraph("\n"));

            // 4. Patient Information Table
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(90);
            table.setWidths(new float[]{2f, 0.2f, 5f});
            table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

            String nama = rm.getPasien() != null ? rm.getPasien().getNama() : "-";
            String umur = rm.getPasien() != null ? String.valueOf(rm.getPasien().getUmur()) + " tahun" : "-";
            String gender = rm.getPasien() != null ? rm.getPasien().getGender() : "-";
            String alamat = rm.getPasien() != null ? rm.getPasien().getAlamat() : "-";

            addTableRow(table, "Nama", ":", nama, normalFont);
            addTableRow(table, "Umur", ":", umur, normalFont);
            addTableRow(table, "Jenis Kelamin", ":", gender, normalFont);
            addTableRow(table, "Alamat", ":", alamat, normalFont);
            
            document.add(table);
            document.add(new Paragraph("\n"));

            // 5. Medical Statement
            Paragraph statement = new Paragraph();
            statement.add(new Chunk("Berdasarkan hasil pemeriksaan medis, pasien tersebut didiagnosis mengalami: ", normalFont));
            statement.add(new Chunk(rm.getDiagnosis(), boldFont));
            document.add(statement);
            
            document.add(new Paragraph("\nOleh karena itu, yang bersangkutan memerlukan istirahat selama 3 (tiga) hari, terhitung sejak tanggal dikeluarkan surat ini.", normalFont));
            document.add(new Paragraph("Demikian surat keterangan ini dibuat untuk digunakan sebagaimana mestinya.\n\n", normalFont));

            // 6. Signature and Validation Section
            PdfPTable sigTable = new PdfPTable(2);
            sigTable.setWidthPercentage(100);
            
            PdfPCell validationCell = new PdfPCell();
            validationCell.setBorder(PdfPCell.NO_BORDER);
            validationCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            validationCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            
            // Add QR Code
            String validationCode = "VERIFIED-SMARTCLINIC-RM-" + rm.getIdRekamMedis() + "-" + LocalDate.now().toString();
            Image qrImage = getQRCodeImage(validationCode, 100, 100);
            if(qrImage != null) {
                qrImage.scaleAbsolute(60f, 60f);
                Chunk qrChunk = new Chunk(qrImage, 0, 0, true);
                Paragraph qrPara = new Paragraph(qrChunk);
                qrPara.setAlignment(Element.ALIGN_LEFT);
                validationCell.addElement(qrPara);
                validationCell.addElement(new Paragraph("Scan QR untuk validasi keaslian surat", new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY)));
            }
            sigTable.addCell(validationCell);

            PdfPCell sigCell = new PdfPCell();
            sigCell.setBorder(PdfPCell.NO_BORDER);
            sigCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            sigCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String tglStr = LocalDate.now().format(formatter);
            String docName = rm.getDokter() != null ? rm.getDokter().getNama() : "Dokter Pemeriksa";
            
            Paragraph dateP = new Paragraph("Kota Sehat, " + tglStr, normalFont);
            dateP.setAlignment(Element.ALIGN_RIGHT);
            sigCell.addElement(dateP);
            
            Paragraph docTitle = new Paragraph("Dokter Pemeriksa,\n\n\n\n\n", normalFont);
            docTitle.setAlignment(Element.ALIGN_RIGHT);
            sigCell.addElement(docTitle);
            
            Paragraph docNameP = new Paragraph(docName, boldFont);
            docNameP.setAlignment(Element.ALIGN_RIGHT);
            sigCell.addElement(docNameP);
            
            // Dummy spacer to match the "Scan QR..." text on the left so "Dr. Andi" aligns with the QR Code
            Paragraph dummySpacer = new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.WHITE));
            dummySpacer.setAlignment(Element.ALIGN_RIGHT);
            sigCell.addElement(dummySpacer);
            
            sigTable.addCell(sigCell);
            document.add(sigTable);

            document.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void addTableRow(PdfPTable table, String col1, String col2, String col3, Font font) {
        PdfPCell cell1 = new PdfPCell(new Phrase(col1, font));
        cell1.setBorder(PdfPCell.NO_BORDER);
        PdfPCell cell2 = new PdfPCell(new Phrase(col2, font));
        cell2.setBorder(PdfPCell.NO_BORDER);
        PdfPCell cell3 = new PdfPCell(new Phrase(col3, font));
        cell3.setBorder(PdfPCell.NO_BORDER);
        
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
    }
}
