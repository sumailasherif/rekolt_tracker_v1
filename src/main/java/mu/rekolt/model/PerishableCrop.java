package mu.rekolt.model;

//I extended Produce so PerishableCrop could inherit shared attributes like code, weight, and grade calculation
public class PerishableCrop extends Produce {
    //I stored the price per kg locally since base unit prices differed across crop categories
    private final double pricePerKg;

    //This is where I passed shared properties up to Produce and kept pricePerKg local to this class
    public PerishableCrop(String produceCode, double produceWeightKg, int qualityScore, double pricePerKg) {
        super(produceCode, produceWeightKg, qualityScore);
        this.pricePerKg = pricePerKg;
    }

    //This is where I supplied the perishable unit price implementation to Produce's template method
    @Override
    protected double unitPrice() {
        return pricePerKg;
    }

    //I hardcoded this return value to 0.90 as required by the payment rules for perishable produce
    @Override
    public double categoryMultiplier() {
        return 0.90;
    }

    //This is where I provided the category label for report and display formatting
    @Override
    public String getCategoryName() {
        return "Perishable Crop";
    }
}