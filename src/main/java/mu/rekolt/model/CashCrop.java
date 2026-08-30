package mu.rekolt.model;

public class CashCropProduce extends Produce {
    //I store this here instead of in Produce, since price per kg only makes sense for one produce type at a time
    private final double price;

    //This is where I pass code, mass and quality up to Produce, and keep price local to this class
    public CashCropProduce(String code, double massKg, int qualityScore, double price) {
        super(code, massKg, qualityScore);
        this.price = price;
    }