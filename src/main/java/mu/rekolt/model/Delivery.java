package mu.rekolt.model;

import mu.rekolt.util.IDGenerator;

import java.util.ArrayList;
import java.util.Scanner;


public class Delivery implements Comparable<Delivery> {// we make every field private and final because a delivery's details never changes
    private  final String deliveryId;
    private  final String produceCode;
    private  final double produceWeightKg;
    private  final String memberName;
    private  final int qualityScore;
    private final String memberId;
    private final int deliveryWeek;

    private static final ArrayList<Delivery> deliveries = new ArrayList<>();

    //I computed and stored these once,  in the constructor, instead of recalculating them every time we read them
    private  String grade;
    private  double commissionAmount;
    private  double transportLevyAmount;
    private  double netPayable;


        //This is where I define my Getters & Setters
        public String getDeliveryId() {
            return deliveryId;
        }

        public String getProduceCode() {
            return produceCode;
        }

        public double getProduceWeightKg() {
            return produceWeightKg;
        }

        public int getQualityScore() {
            return qualityScore;
        }

        public int getDeliveryWeek() {
            return deliveryWeek;
        }

        public String getMemberId() {
            return memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public double getNetPayable() {
            return netPayable;
        }

        public void setNetPayable(double netPayable) {
            this.netPayable = netPayable;
        }

        public double getCommissionAmount() {
            return commissionAmount;
        }


        public void setCommissionAmount(double commissionAmount) {
            this.commissionAmount = commissionAmount;
        }

        public double getTransportLevyAmount() {
            return transportLevyAmount;
        }

        public void setTransportLevyAmount(double transportLevyAmount) {
            this.transportLevyAmount = transportLevyAmount;
        }

    // This is the constructor for Delivery class
    public Delivery(String deliveryId, String produceCode, String memberId, String memberName,
                    double produceWeightKg, int qualityScore, int deliveryWeek) {
        this.deliveryId = deliveryId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.produceCode = produceCode;
        this.produceWeightKg = produceWeightKg;
        this.qualityScore = qualityScore;
        this.deliveryWeek = deliveryWeek;
        this.grade = QualityGrade.fromScore(qualityScore);
    }

    @Override
    public int compareTo(Delivery other) {
        // This is done in descending order, that is, highest net payable value comes first.
        return Double.compare(other.netPayable, this.netPayable);
    }
    // Main recordDelivery function - to be used in main
    public static String recordDelivery(Scanner scanner) {
        String deliveryId = "D-%d".formatted(IDGenerator.getNextId());
        String produceCode;
        double produceWeightKg;
        int qualityScore;
        int deliveryWeek;
        String memberId;
        String memberName;
        boolean valid = true;

        System.out.println("Log a New Delivery");
        System.out.println("---------------------\n");
        do {
            System.out.print("Enter Member ID: ");
            memberId = scanner.nextLine();