package mu.rekolt.app;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD






        // 1. Declare inputs
//        double mass = 236.0;
//        double basePrice = 90.0;
//
//        // 2. Perform the step calculations
//        double baseValue = mass * basePrice;
//
//        double gradeMultiplier = 1.15; // Grade A
//        double gradeValue = baseValue * gradeMultiplier;
//
//        double categoryMultiplier = 1.00; // Cereal (Beans)
//        double categoryValue = gradeValue * categoryMultiplier;
//
//        double commission = categoryValue * 0.05;
//        double transportLevy = mass * 2.0;
//
//        double netPayable = categoryValue - commission - transportLevy;
//
//        // 3. Display the results
//        System.out.println("Net Payable: " + netPayable);
=======
        // 1. We declare inputs
        double mass = 236.0;
        double basePrice = 90.0;
        // This helps us to calculate the Base Value. we use double to cater for decimals
        double baseValue = mass * basePrice;

        // We declare Grade Multiplier (Grade A = 1.15)
        double gradeMultiplier = 1.15;
        double gradeValue = baseValue * gradeMultiplier;

        //We apply Category Multiplier (Cereal = 1.00)
        double categoryMultiplier = 1.00;
        double categoryValue = gradeValue * categoryMultiplier;

        //we calculate the commission (5% or 0.05)
        double commission = categoryValue * 0.05;

        //transport levy is MUR 2 per kg
        double transportLevy = mass * 2.0;

        //Net Payable is calculated difference between category value and commission and transport
        double netPayable = categoryValue - commission - transportLevy;
        System.out.println("Net Payable: " + netPayable);
>>>>>>> 5cdbbc36409235d21f3c8184b7d330ebcbf2bcd2
    }
}