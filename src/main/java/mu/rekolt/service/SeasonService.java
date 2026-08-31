package mu.rekolt.service;

import mu.rekolt.model.Delivery;
import mu.rekolt.model.MemberFarmer;
import mu.rekolt.util.IDGenerator;

import java.util.*;

public class SeasonService {

    private final double[][] weeklyGrid = new double[21][4];
    private final List<Delivery> deliveries = new ArrayList<>();
    private final PayoutCalculator payoutCalculator = new PayoutCalculator();

    // I switched memberTotals to TreeMap to maintain natural alphabetical order by member ID
    private final Map<String, Double> memberTotals = new TreeMap<>();

    // I switched deliveriesByMember to LinkedHashMap to preserve exact chronological delivery sequence
    private final Map<String, List<Delivery>> deliveriesByMember = new LinkedHashMap<>();

    private final Set<String> memberIds = new HashSet<>();
    private final Map<String, MemberFarmer> members = new HashMap<>();

    public String addDelivery(String memberId, String memberName, String produceCode, double massKg, int score, int week) {
        String deliveryId = "D-%d".formatted(IDGenerator.getNextId());
        Delivery delivery = new Delivery(deliveryId, produceCode, memberId, memberName, massKg, score, week);

        PayoutCalculator.PayoutResult payout = payoutCalculator.calculate(delivery);
        delivery.setCommissionAmount(payout.commissionValue());
        delivery.setTransportLevyAmount(payout.transportLevyValue());
        delivery.setNetPayable(payout.netPayableValue());

        deliveries.add(delivery);
        weeklyGrid[week][produceColumnIndex(produceCode)] += massKg;
        memberTotals.put(memberId, memberTotals.getOrDefault(memberId, 0.0) + payout.netPayableValue());

        deliveriesByMember.computeIfAbsent(memberId, k -> new ArrayList<>()).add(delivery);

        memberIds.add(memberId);
        members.putIfAbsent(memberId, new MemberFarmer(memberId, memberName));

        printDeliveryBreakdown(delivery, massKg, payout);

        return deliveryId;
    }

    private void printDeliveryBreakdown(Delivery delivery, double massKg, PayoutCalculator.PayoutResult payout) {
        String massText;
        if (massKg == (long) massKg) {
            massText = String.format("%.0f", massKg);
        } else {
            massText = String.valueOf(massKg);
        }

        System.out.printf("Delivery %s recorded. Grade %s%n", delivery.getDeliveryId(), delivery.getGrade());
        printCalculationLine("Base value", massText, "=", payout.baseValue());
        printCalculationLine("Grade " + delivery.getGrade(), "", "=", payout.gradedValue());
        printCalculationLine("Category", "", "=", payout.categoryValue());
        printCalculationLine("Commission 5%", "", "-", payout.commissionValue());
        printCalculationLine("Transport levy", massText + " x 2.00", "-", payout.transportLevyValue());
        System.out.printf("    %-20s %-18s = %,12.2f MUR%n",
                "NET PAYABLE", "", payout.netPayableValue());
    }

    private void printCalculationLine(String label, String calculation, String symbol, double amount) {
        System.out.printf("    %-20s %-18s %s %,12.2f%n",
                label, calculation, symbol, amount);
    }

    private int produceColumnIndex(String produceCode) {
        return switch (produceCode) {
            case "MZE" -> 0;
            case "BNS" -> 1;
            case "POT" -> 2;
            case "TEA" -> 3;
            default -> throw new IllegalArgumentException("Unknown produce code: " + produceCode);
        };
    }

    public void printMemberTotals() {
        System.out.println("Total payment per member (MUR)");
        for (Map.Entry<String, Double> entry : memberTotals.entrySet()) {
            System.out.printf("%s   %s   %.2f%n", entry.getKey(), members.get(entry.getKey()).getMemberName(), entry.getValue());
        }
    }

    public void printWeeklyGrid() {
        System.out.println("Weekly volume grid (kg)");
        System.out.println("Week    MZE     BNS     POT     TEA     Total");
        for (int week = 1; week <= 20; week++) {
            double weekTotal = 0;
            for (int col = 0; col < 4; col++) {
                weekTotal += weeklyGrid[week][col];
            }
            if (weekTotal > 0) {
                System.out.printf("%-8d%-8.1f%-8.1f%-8.1f%-8.1f%-8.1f%n",
                        week, weeklyGrid[week][0], weeklyGrid[week][1],
                        weeklyGrid[week][2], weeklyGrid[week][3], weekTotal);
            }
        }
    }

    // I refactored topDeliveriesByValue to use Stream sorting and limiting
    public List<Delivery> topDeliveriesByValue(int n) {
        return deliveries.stream()
                .sorted(Comparator.comparingDouble(Delivery::getNetPayable).reversed())
                .limit(n)
                .toList();
    }

    // I simplified findMemberById to directly lookup the key without redundant condition checks
    public MemberFarmer findMemberById(Map<String, MemberFarmer> membersMap, String id) {
        return membersMap.get(id);
    }

    public MemberFarmer findMemberById(String id) {
        return findMemberById(members, id);
    }

    // I replaced the Iterator loop with Stream filtering for cleaner list generation
    public static List<Delivery> excludingRejected(List<Delivery> deliveries) {
        return deliveries.stream()
                .filter(d -> !"REJECT".equalsIgnoreCase(d.getGrade()))
                .toList();
    }

    public List<MemberFarmer> getSortedMembers() {
        List<MemberFarmer> list = new ArrayList<>(members.values());
        list.sort(Comparator.comparing(MemberFarmer::getMemberId));
        return list;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public List<Delivery> getDeliveriesForMember(String memberId) {
        return deliveriesByMember.getOrDefault(memberId, Collections.emptyList());
    }

    // I updated getSeasonTotal to use double stream mapping
    public double getSeasonTotal() {
        return memberTotals.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
