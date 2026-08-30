package mu.rekolt.model;

public class QualityGrade {

    private QualityGrade() {
        // utility class, no instances
    }

    public static String fromScore(int qualityScore) {
        if (qualityScore < 0 || qualityScore > 100) {
            throw new IllegalArgumentException("Quality score must be between 0 and 100");
        }
        if (qualityScore >= 90) {
            return "A";
        } else if (qualityScore >= 75) {
            return "B";
        } else if (qualityScore >= 50) {
            return "C";
        } else {
            return "D";
        }
    }
}