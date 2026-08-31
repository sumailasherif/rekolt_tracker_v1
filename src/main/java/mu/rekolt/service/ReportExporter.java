package mu.rekolt.service;

import mu.rekolt.model.Delivery;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

// ReportExporter class handles Word document generation for season summaries and member statements
public class ReportExporter {

    private static final String OUTPUT_PATH = "output/season-report.docx";

    public void generateSeasonReport(SeasonService seasonService) {
        try (XWPFDocument document = new XWPFDocument()) {

            // I extracted sorted member IDs and calculated the overall season payment total
            TreeSet<String> sortedMemberIds = new TreeSet<>(seasonService.getSortedMembers()
                    .stream()
                    .map(m -> m.getMemberId())
                    .toList());
            double seasonTotal = seasonService.getSeasonTotal();

            writeSummarySection(document, sortedMemberIds, seasonService);

            // I iterated through members to generate individual delivery statements and page breaks
            for (String memberId : sortedMemberIds) {
                List<Delivery> memberDeliveries = seasonService.getDeliveriesForMember(memberId);
                double memberTotal = memberDeliveries.stream()
                        .mapToDouble(Delivery::getNetPayable)
                        .sum();

                XWPFParagraph pageBreak = document.createParagraph();
                pageBreak.createRun().addBreak(BreakType.PAGE);

                writeMemberSection(document, memberId, memberDeliveries, memberTotal);
            }

            writeClosingSection(document, seasonTotal);

            // I wrote the document buffer to the destination output stream with error logging
            try (FileOutputStream out = new FileOutputStream(OUTPUT_PATH)) {
                document.write(out);
            }

            System.out.println("Writing " + OUTPUT_PATH + " ... " + sortedMemberIds.size() + " member sections, done.");

        } catch (IOException e) {
            System.out.println("Could not write the season report. Check that the 'output' folder exists "
                    + "and is not open in another program, then try again. (" + e.getMessage() + ")");
        }
    }

    private void writeSummarySection(XWPFDocument document, Set<String> sortedMemberIds, SeasonService seasonService) {
        heading(document, "Season Report", 16);

        // I built the total payment per member table from sorted member records
        heading(document, "Total payment per member (MUR)", 12);
        XWPFTable memberTable = document.createTable(sortedMemberIds.size() + 1, 2);
        setRow(memberTable, 0, "Member", "Total payment (MUR)");
        int row = 1;
        for (String memberId : sortedMemberIds) {
            double total = seasonService.getDeliveriesForMember(memberId).stream()
                    .mapToDouble(Delivery::getNetPayable)
                    .sum();
            setRow(memberTable, row++, memberId, String.format("%,.2f", total));
        }

        // I populated the top five deliveries table ranked by highest net payable value
        heading(document, "Top five deliveries by value", 12);
        List<Delivery> top = seasonService.topDeliveriesByValue(5);
        XWPFTable topTable = document.createTable(top.size() + 1, 6);
        setRow(topTable, 0, "Rank", "Delivery ID", "Member", "Produce", "Mass (kg)", "Net payable (MUR)");
        for (int i = 0; i < top.size(); i++) {
            Delivery d = top.get(i);
            setRow(topTable, i + 1,
                    String.valueOf(i + 1),
                    d.getDeliveryId(),
                    d.getMemberId(),
                    d.getProduceCode(),
                    String.format("%.1f", d.getProduceWeightKg()),
                    String.format("%,.2f", d.getNetPayable()));
        }
    }

    private void heading(XWPFDocument document, String text, int fontSize) {
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.setBold(true);
        run.setFontSize(fontSize);
        run.setText(text);
    }

    private void writeMemberSection(XWPFDocument document, String memberId,
                                    List<Delivery> deliveries, double memberTotal) {

        String memberName = deliveries.isEmpty() ? "" : deliveries.get(0).getMemberName();

        XWPFParagraph heading = document.createParagraph();
        XWPFRun headingRun = heading.createRun();
        headingRun.setBold(true);
        headingRun.setFontSize(14);
        headingRun.setText(memberId + " - " + memberName);

        // I initialized the member statement table and accumulated commission and transport levy metrics
        XWPFTable table = document.createTable(deliveries.size() + 1, 5);
        setRow(table, 0, "Delivery ID", "Produce", "Mass (kg)", "Grade", "Net Payable (MUR)");

        double totalCommission = 0.0;
        double totalLevy = 0.0;

        for (int i = 0; i < deliveries.size(); i++) {
            Delivery d = deliveries.get(i);
            setRow(table, i + 1,
                    d.getDeliveryId(),
                    d.getProduceCode(),
                    String.format("%.1f", d.getProduceWeightKg()),
                    d.getGrade(),
                    String.format("%,.2f", d.getNetPayable()));
            totalCommission += d.getCommissionAmount();
            totalLevy += d.getTransportLevyAmount();
        }

        // I added total commission, transport levy, net payable text, and a signature line
        addLine(document, String.format("Commission: %,.2f MUR", totalCommission));
        addLine(document, String.format("Transport levy: %,.2f MUR", totalLevy));

        XWPFParagraph netPara = document.createParagraph();
        XWPFRun netRun = netPara.createRun();
        netRun.setBold(true);
        netRun.setText(String.format("NET PAYABLE: %,.2f MUR", memberTotal));

        addLine(document, " ");
        addLine(document, "Signature: _______________________________");
    }

    private void writeClosingSection(XWPFDocument document, double seasonTotal) {
        XWPFParagraph pageBreak = document.createParagraph();
        pageBreak.createRun().addBreak(BreakType.PAGE);

        XWPFParagraph heading = document.createParagraph();
        XWPFRun headingRun = heading.createRun();
        headingRun.setBold(true);
        headingRun.setFontSize(14);
        headingRun.setText("Season totals");

        XWPFParagraph totalPara = document.createParagraph();
        XWPFRun totalRun = totalPara.createRun();
        totalRun.setBold(true);
        totalRun.setText(String.format("TOTAL PAID THIS SEASON: %,.2f MUR", seasonTotal));
    }

    // I added helper methods to streamline row population and paragraph text appending
    private void setRow(XWPFTable table, int rowIndex, String... values) {
        XWPFTableRow row = table.getRow(rowIndex);
        for (int col = 0; col < values.length; col++) {
            row.getCell(col).setText(values[col]);
        }
    }

    private void addLine(XWPFDocument document, String text) {
        document.createParagraph().createRun().setText(text);
    }
}