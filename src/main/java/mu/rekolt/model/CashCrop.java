package mu.rekolt.model;

public class CashCrop extends Produce {
    //I store this here instead of in Produce, since price per kg only makes sense for one produce type at a time
    private final double price;

    //This is where I pass produceCode, produceWeightKg and quality up to Produce, and keep price local to this class
    public CashCrop(String produceCode, double produceWeightKg, int qualityScore, double price) {
        super(produceCode, produceWeightKg, qualityScore);
        this.price = price;
    }

    //This is where I plug my own price into Produce's getUnitPrice(), instead of Produce needing to know how each crop is priced
    @Override
    protected double unitPrice() {
        return price;
    }

    //I hardcoded this instead of taking it as a constructor argument, since every cash crop gets the same bonus
    @Override
    public double categoryMultiplier() {
        return 1.10;
    }

    //This is where I name the category for reports and receipts
    @Override
    public String getCategoryName() {
        return "Cash Crop";
    }
}