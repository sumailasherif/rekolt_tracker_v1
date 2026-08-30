package mu.rekolt.model;

public abstract class Produce {
    private final String produceCode;//I keep core produce attributes immutable here so they cannot be altered once registered
    private final double produceWeightKg;
    private final int qualityScore;
    private final String grade;

    //This is where I initialize shared properties and automatically derive the grade based on the quality score
    protected Produce(String produceCode, double produceWeightKg, int qualityScore) {
        this.produceCode = produceCode;
        this.produceWeightKg = produceWeightKg;
        this.qualityScore = qualityScore;
        this.grade = QualityGrade.fromScore(qualityScore);
    }

    //I expose these getters so other modules can retrieve the unique item identifier
    public String getProduceCode() {
        return produceCode;
    }

    public double getProduceWeightKg() {
        return produceWeightKg;
    }

    public int getQualityScore() {
        return qualityScore;
    }

    public String getGrade() {
        return grade;
    }

    // Template method: subclasses supply their own price via unitPrice()
    public double getUnitPrice() {
        return unitPrice();
    }


    //I force each specific produce subclass to implement its own unit pricing calculation
    protected abstract double unitPrice();

    public abstract double categoryMultiplier();

    public abstract String getCategoryName();
}