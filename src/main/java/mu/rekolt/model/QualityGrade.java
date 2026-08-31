package mu.rekolt.model;

public class QualityGrade {

    private QualityGrade() {
        // utility class, no instances
    }

    public static String fromScore(int qualityScore) {
        if (qualityScore < 0 || qualityScore > 100) {
            throw new IllegalArgumentException("Quality score must be between 0 and 100");
        }
        if (qualityScore >= 85) {
            return "A";
        } else if (qualityScore >= 70) {
            return "B";
        } else if (qualityScore >= 50) {
            return "C";
        } else {
            return "REJECT";
        }
    }

    public static double multiplierFor(String grade) {
        return switch (grade) {
            case "A" -> 1.15;
            case "B" -> 1.00;
            case "C" -> 0.85;
            case "REJECT" -> 0.00;
            default -> throw new IllegalArgumentException("Unknown grade: " + grade);
        };
    }
}