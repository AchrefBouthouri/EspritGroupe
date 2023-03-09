package com.pidev.esprit.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pidev.esprit.model.Facture;
import com.pidev.esprit.model.RDV;
import com.pidev.esprit.model.Reclamation;
import com.pidev.esprit.repository.FactureRepository;
import com.pidev.esprit.repository.RDVRepository;
import com.pidev.esprit.repository.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PDFGeneratorService {

    @Autowired
    FactureRepository factureRepository;
    @Autowired
    ReclamationRepository reclamationRepository;
    @Autowired
    RDVRepository rdvRepository;

    public void export(HttpServletResponse response , long idFacture) throws IOException, DocumentException, URISyntaxException {
        Facture facture = factureRepository.findById(idFacture).get();
        RDV rdv = rdvRepository.findById(facture.getRdv().getId()).get();
        Reclamation reclamation = reclamationRepository.findById(facture.getRdv().getReclamation().getId()).get();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        response.setContentType("application/pdf");
        Path path = Paths.get(ClassLoader.getSystemResource("espritGroupe.png").toURI());

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Facture_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        document.open();
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.setAlignment(Image.ALIGN_RIGHT);
        document.add(img);

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph title = new Paragraph("Facture " + facture.getRef(), fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        Font fontLabel = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontLabel.setSize(12);

        Font fontValue = FontFactory.getFont(FontFactory.HELVETICA);
        fontValue.setSize(12);


        PdfPTable tableInf = new PdfPTable(1);
        tableInf.setWidthPercentage(100);
        tableInf.setSpacingBefore(10f);
        tableInf.setSpacingAfter(10f);

        //addTableRow(tableInf, "Client", reclamation.getCreatedBy().+" "+reclamation.getCreatedBy().getLastname(), fontLabel, fontValue);
        addTableRow(tableInf, "", "", fontLabel, fontValue);
        //addTableRow(tableInf, "Technician",rdv.getTechnicien().getFirstname()+" "+rdv.getTechnicien().getFirstname(), fontLabel, fontValue);
        addTableRow(tableInf, "", "", fontLabel, fontValue);
        addTableRow(tableInf, "Date",facture.getDateFacture().toString(), fontLabel, fontValue);
        addTableRow(tableInf, "", "", fontLabel, fontValue);
        addTableRow(tableInf, "Description", facture.getDescription(), fontLabel, fontValue);
        addTableRow(tableInf, "", "", fontLabel, fontValue);
        document.add(tableInf);

        PdfPTable tableRec = new PdfPTable(1);
        addTableRow(tableRec, "Reclamation", "", fontLabel, fontValue);
        addTableRow(tableRec, "", "Ref: "+reclamation.getId(), fontLabel, fontValue);
        addTableRow(tableRec, "", "", fontLabel, fontValue);
        addTableRow(tableRec, "Type",reclamation.getType().toString(), fontLabel, fontValue);
        addTableRow(tableRec, "", "", fontLabel, fontValue);
        addTableRow(tableRec, "Date Creation",reclamation.getCreatedAt().toString(), fontLabel, fontValue);
        addTableRow(tableRec, "", "", fontLabel, fontValue);
        //addTableRow(tableRec, "Date Confirmation",reclamation.getConfirmedAt().toString(), fontLabel, fontValue);
        //addTableRow(tableRec, "", "", fontLabel, fontValue);
        document.add(tableRec);

        PdfPTable tablePay = new PdfPTable(2);
        addTableRowWithBorder(tablePay, "Total", facture.getTotal()+" TND", fontLabel, fontValue);
        addTableRowWithBorder(tablePay, "TVA",((facture.getTotal()*7)/100)+" TND", fontLabel, fontValue);
        addTableRowWithBorder(tablePay, "TTC", (facture.getTotal()+((facture.getTotal()*7)/100))+" TND", fontLabel, fontValue);

        // Align the values to the right
        for (int i = 0; i < tablePay.getRows().size(); i++) {
            PdfPCell[] cells = tablePay.getRow(i).getCells();
            cells[1].setHorizontalAlignment(Element.ALIGN_RIGHT);
        }
        document.add(tablePay);
        document.close();
    }

    private void addTableRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell cell1 = new PdfPCell(new Paragraph(label, labelFont));
        cell1.setBorder(Rectangle.NO_BORDER);
        PdfPCell cell2 = new PdfPCell(new Paragraph(value, valueFont));
        cell2.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell1);
        table.addCell(cell2);
    }

    private void addTableRowWithBorder(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell cell1 = new PdfPCell(new Paragraph(label, labelFont));
        cell1.setBorder(Rectangle.BOX);
        PdfPCell cell2 = new PdfPCell(new Paragraph(value, valueFont));
        cell2.setBorder(Rectangle.BOX);
        table.addCell(cell1);
        table.addCell(cell2);
    }


}