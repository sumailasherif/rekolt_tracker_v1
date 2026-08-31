package mu.rekolt.model;

import mu.rekolt.service.SeasonService;

import java.util.Scanner;


public class Delivery implements Comparable<Delivery> {// we make every field private and final because a delivery's details never changes
    private  final String deliveryId;
    private  final String produceCode;
    private  final double produceWeightKg;
    private  final String memberName;
    private  final int qualityScore;
    private final String memberId;
    private final int deliveryWeek;

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
    // This is our Main recordDelivery function which we will use in main
    public static String recordDelivery(Scanner scanner, SeasonService seasonService) {
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

            if (!memberId.matches("M-\\d{4}")) { //This allows use to validate Member code format
                valid = false;
                System.out.println("That member ID doesn't look right - please try again!");
            } else {
                valid = true;
                break;
            }
        } while (!valid);

        do {
            System.out.print("Enter Member Name: ");

            memberName = scanner.nextLine();
            if (memberName.isEmpty()) { // This helps to check  if member has inputted a name
                valid = false;
                System.out.println("Member name can't be blank - please enter one!");
            } else {
                valid = true;
                break;
            }
        } while (!valid);
        do {
            System.out.print("Enter Produce Code: ");

            produceCode = scanner.nextLine();
            if (produceCode.contentEquals("MZE") || produceCode.contentEquals("BNS") || // This allows use to validate Member code format
                    produceCode.contentEquals("POT") || produceCode.contentEquals("TEA")) {
                valid = true;
                break;
            } else {
                valid = false;
                System.out.println("That's not a recognized produce code!");
                System.out.println("""
                Accepted Produce Codes:
                MZE - Maize
                BNS - Beans
                POT - Potato
                TEA - Green Tea Leaf""");
            }
        } while (!valid);

        do {
            System.out.print("Produce Mass (KG): ");

            produceWeightKg = scanner.nextDouble();
            if (produceWeightKg <= 0 || produceWeightKg > 5000) { // This allows use to validate Member code format min & max mass
                System.out.println("That mass is out of range - we can only take deliveries up to 5000KG!");
                valid = false;
            } else {
                valid = true;
                break;
            }
        } while (!valid);

        do {
            System.out.print("Enter Quality Score: ");

            qualityScore = scanner.nextInt();
            if (qualityScore < 0 || qualityScore > 100) { //This allows use to validate min & max quality score
                System.out.println("Quality score has to be between 0 and 100 - try again.");
                valid = false;
            } else {
                valid = true;
                break;
            }
        } while (!valid);

        do {
            System.out.print("Enter Delivery Week: ");
            deliveryWeek = scanner.nextInt();
            if (deliveryWeek < 1 || deliveryWeek > 20) { // This allows use to validate available weeks
                System.out.println("That week's out of range - please enter a valid one!");
                valid = false;
            } else {
                valid = true;
                break;
            }
        } while (!valid);
        scanner.nextLine(); // consume trailing newline left by the last nextInt()

        return seasonService.addDelivery(memberId, memberName, produceCode, produceWeightKg, qualityScore, deliveryWeek);
    }
}