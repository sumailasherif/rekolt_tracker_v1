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
