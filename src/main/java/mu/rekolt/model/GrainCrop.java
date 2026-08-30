package mu.rekolt.model;

//I extend Produce so GrainCrop inherits shared characteristics like code, weight, and grade logic
public class GrainCrop extends Produce {
    //I store the base price per kg here since it applies specifically to grain crop items
    private final double pricePerKg;

    //This is where I pass shared properties up to Produce and keep pricePerKg local to this class
    public GrainCrop(String produceCode, double produceWeightKg, int qualityScore, double pricePerKg) {
        super(produceCode, produceWeightKg, qualityScore);
        this.pricePerKg = pricePerKg;
    }
    //This is where I supply the grain unit price implementation to Produce's template method
    @Override
    protected double unitPrice() {
        return pricePerKg;
    }

    //I hardcoded this return value to 1.00 as required by the payment rules for Cereal/Grain produce
    @Override
    public double categoryMultiplier() {
        return 1.00;
    }

    //This is where I provide the category label for display and report formatting
    @Override
    public String getCategoryName() {
        return "Grain Crop";
    }
}