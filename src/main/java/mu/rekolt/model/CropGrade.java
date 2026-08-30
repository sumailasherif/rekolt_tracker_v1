package mu.rekolt.model;

//I created this enum to encapsulate the grade classifications alongside their payment multipliers
public enum CropGrade {
    A(1.15),
    B(1.00),
    C(0.85),
    REJECT(0.00);

    //I stored the grade multiplier as a final field so each constant carries its own weight calculation factor
    private final double multiplier;

    //I defined this constructor to bind each enum constant to its exact spec multiplier
    CropGrade(double multiplier) {
        this.multiplier = multiplier;
    }

    //I provided this getter to allow other modules to retrieve the numerical grade multiplier
    public double getMultiplier() {
        return multiplier;
    }

    //I implemented this utility method to derive the correct grade constant based on the numeric quality score
    public static String fromScore(int score) {
        if (score >= 85) {
            return A.name();
        } else if (score >= 70) {
            return B.name();
        } else if (score >= 50) {
            return C.name();
        } else {
            return REJECT.name();
        }
    }
}