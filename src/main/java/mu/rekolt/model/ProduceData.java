package mu.rekolt.model;

public class ProduceData {

    //I defined this static price list array to store preset crop types and their base prices per kilogram
    public static final Produce[] PRICE_LIST = {
            new GrainCrop("MZE", 0.0, 0, 30.0),      // Maize
            new GrainCrop("BNS", 0.0, 0, 90.0),      // Beans
            new PerishableCrop("POT", 0.0, 0, 45.0), // Potatoes
            new CashCrop("TEA", 0.0, 0, 25.0)        // Green Tea Leaf
    };

    //I implemented this lookup method to return the corresponding Produce template based on a given code
    public static Produce findByCode(String code) {
        if (code == null) {
            return null;
        }
        return switch (code.toUpperCase()) {
            case "MZE" -> PRICE_LIST[0];
            case "BNS" -> PRICE_LIST[1];
            case "POT" -> PRICE_LIST[2];
            case "TEA" -> PRICE_LIST[3];
            default -> null;
        };
    }

    //I created this helper method to map produce codes directly to their array index for grid lookup operations
    public static int indexOf(String code) {
        if (code == null) {
            return -1;
        }
        return switch (code.toUpperCase()) {
            case "MZE" -> 0;
            case "BNS" -> 1;
            case "POT" -> 2;
            case "TEA" -> 3;
            default -> -1;
        };
    }
}