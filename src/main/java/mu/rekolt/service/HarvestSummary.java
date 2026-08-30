package mu.rekolt.service;

import mu.rekolt.model.*;

import java.lang.reflect.Member;
import java.util.*;
import Delivery.;

public class SeasonService {

    private final double[][] weeklyGrid = new double[21][4];
    private final List<Deliveries> deliveries = new ArrayList<>();

    // I switched memberTotals to TreeMap to maintain natural alphabetical order by member ID
    private final Map<String, Double> memberTotals = new TreeMap<>();

    // I switched deliveriesByMember to LinkedHashMap to preserve exact chronological delivery sequence
    private final Map<String, List<Deliveries>> deliveriesByMember = new LinkedHashMap<>();

    private final Set<String> memberIds = new HashSet<>();
    private final Map<String, Member> members = new HashMap<>();

    public void addDelivery(String memberId, String memberName, String produceCode, double massKg, int score, int week) {
        Grade grade = Grade.fromScore(score);
        String deliveryId = "D-" + (1000 + deliveries.size() + 1);
        Deliveries delivery = new Deliveries(
                deliveryId, memberId, memberName, produceCode, massKg, score, week);
        double netPayable = delivery.netPayable();

        deliveries.add(delivery);
        weeklyGrid[week][produceColumnIndex(produceCode)] += massKg;
        memberTotals.put(memberId, memberTotals.getOrDefault(memberId, 0.0) + netPayable);

        deliveriesByMember.computeIfAbsent(memberId, k -> new ArrayList<>()).add(delivery);

        memberIds.add(memberId);
        members.putIfAbsent(memberId, new Member(memberId, memberName));

        printDeliveryBreakdown(delivery);
    }

    private void printDeliveryBreakdown(Deliveries delivery) {
        String massText;
        if (delivery.getMassKg() == (long) delivery.getMassKg()) {
            massText = String.format("%.0f", delivery.getMassKg());
        } else {
            massText = String.valueOf(delivery.getMassKg());
        }

        System.out.printf("Delivery %s recorded. Grade %s%n", delivery.getId(), delivery.getGrade());
        printCalculationLine("Base value", massText + " x " + String.format("%.2f", delivery.getUnitPrice()),
                "=", delivery.getBaseValue());
        printCalculationLine("Grade " + delivery.getGrade(), "x " + String.format("%.2f", delivery.getGradeMultiplier()),
                "=", delivery.getGradedValue());
        printCalculationLine(delivery.getProduceCategory(),
                "x " + String.format("%.2f", delivery.getCategoryMultiplier()),
                "=", delivery.getCategoryValue());
        printCalculationLine("Commission 5%", "", "-", delivery.getCommission());
        printCalculationLine("Transport levy", massText + " x 2.00", "-", delivery.getTransportLevy());
        System.out.printf("    %-20s %-18s = %,12.2f MUR%n",
                "NET PAYABLE", "", delivery.getNetPayable());
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
            System.out.printf("%s   %s   %.2f%n", entry.getKey(), members.get(entry.getKey()).getName(), entry.getValue());
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
    public List<Deliveries> topDeliveriesByValue(int n) {
        return deliveries.stream()
                .sorted(Comparator.comparingDouble(Deliveries::getNetPayable).reversed())
                .limit(n)
                .toList();
    }

    // I simplified findMemberById to directly lookup the key without redundant condition checks
    public Member findMemberById(Map<String, Member> membersMap, String id) {
        return membersMap.get(id);
    }

    public Member findMemberById(String id) {
        return findMemberById(members, id);
    }

    // I replaced the Iterator loop with Stream filtering for cleaner list generation
    public static List<Deliveries> excludingRejected(List<Deliveries> deliveries) {
        return deliveries.stream()
                .filter(d -> !"REJECT".equalsIgnoreCase(d.getGrade()))
                .toList();
    }

    public List<Member> getSortedMembers() {
        List<Member> list = new ArrayList<>(members.values());
        Collections.sort(list);
        return list;
    }

    public List<Deliveries> getDeliveries() {
        return deliveries;
    }

    public List<Deliveries> getDeliveriesForMember(String memberId) {
        return deliveriesByMember.getOrDefault(memberId, Collections.emptyList());
    }

    // I updated getSeasonTotal to use double stream mapping
    public double getSeasonTotal() {
        return memberTotals.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}