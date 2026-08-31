package mu.rekolt.service;

import mu.rekolt.model.*;

// PayoutCalculator class handles individual delivery payout logic and print processing
public class PayoutCalculator {

    // I defined constant declarations for financial rates
    private static final double COMMISSION_RATE = 0.05;
    private static final double TRANSPORT_LEVY_PER_KG = 2.0;

    // I added an immutable payout result record to hold financial calculations
    public record PayoutResult(
            double baseValue,
            double gradedValue,
            double categoryValue,
            double commissionValue,
            double transportLevyValue,
            double netPayableValue,
            String grade
    ) {}

    // This method calculates net payable values and returns a PayoutResult object
    public PayoutResult calculate(Delivery delivery) {
        // I made a check to ensure delivery input is valid
        if (delivery == null) {
            throw new IllegalArgumentException("Delivery cannot be null");
        }

        // I added a database check for the corresponding produce code
        Produce produce = ProduceData.findByCode(delivery.getProduceCode());
        if (produce == null) {
            throw new IllegalArgumentException("Invalid produce code: " + delivery.getProduceCode());
        }

        // I extracted variables for quality score and delivery mass
        String grade = QualityGrade.fromScore(delivery.getQualityScore());
        double produceMass = delivery.getProduceWeightKg();

        // An early return with zeroed values if the delivery status is REJECT
        if (grade.equals("REJECT")) {
            return new PayoutResult(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, grade);
        }

        // I initialized local variables from produce and grade data
        double producePrice = produce.getUnitPrice();
        double categoryMultiplier = produce.categoryMultiplier();
        double gradeMultiplier = QualityGrade.multiplierFor(grade);

        double baseValue = produceMass * producePrice;

        double gradedValue = baseValue * gradeMultiplier;

        double categoryValue = gradedValue * categoryMultiplier;

        double commissionValue = categoryValue * COMMISSION_RATE;

        double transportLevyValue = produceMass * TRANSPORT_LEVY_PER_KG;

        double netPayableValue = categoryValue - commissionValue - transportLevyValue;

        return new PayoutResult(baseValue, gradedValue, categoryValue,
                commissionValue, transportLevyValue, netPayableValue, grade);
    }
}