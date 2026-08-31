package mu.rekolt.service;

public class DemoDataSeeder {

    //I defined this initial dataset containing a mix of local and Ghanaian member delivery records with unique seed figures
    private static final Object[][] RAW_DELIVERY_DATA = {
            {"M-0042", "Devi Ramjaun",          "BNS", 248.5,  93,  3},
            {"M-0117", "Jean Ah-Kine",          "MZE", 420.0,  80,  1},
            {"M-0088", "Anisha Beeharry",       "POT", 165.0,  68,  1},
            {"M-0301", "Kwame Osei",            "TEA", 94.0,   88,  2},
            {"M-0056", "Priya Gopal",           "POT", 218.0,  46,  2},
            {"M-0042", "Devi Ramjaun",          "TEA", 91.5,   74,  1},
            {"M-0302", "Ama Mensah",            "BNS", 378.0,  66,  2},
            {"M-0203", "Kevin Appasamy",        "MZE", 188.0,  94,  3},
            {"M-0303", "Kofi Adjei",            "MZE", 305.0,  85,  4},
            {"M-0056", "Priya Gopal",           "BNS", 172.5,  89,  4},
            {"M-0042", "Devi Ramjaun",          "POT", 242.0,  59,  5},
            {"M-0304", "Abena Appiah",          "BNS", 152.0,  97,  5},
            {"M-0117", "Jean Ah-Kine",          "BNS", 368.0,  63,  6},
            {"M-0056", "Priya Gopal",           "POT", 212.0,  51,  7},
            {"M-0301", "Kwame Osei",            "TEA", 108.0,  90,  8},
            {"M-0088", "Anisha Beeharry",       "MZE", 288.0,  83,  9},
            {"M-0305", "Yaw Boateng",           "BNS", 192.0,  86,  10},
            {"M-0042", "Devi Ramjaun",          "POT", 252.0,  57,  11},
            {"M-0304", "Abena Appiah",          "BNS", 138.0,  91,  12},
            {"M-0042", "Devi Ramjaun",          "BNS", 232.0,  88,  13},
            {"M-0117", "Jean Ah-Kine",          "MZE", 398.0,  77,  14},
            {"M-0303", "Kofi Adjei",            "POT", 168.0,  72,  15},
            {"M-0042", "Devi Ramjaun",          "TEA", 96.0,   79,  16},
            {"M-0203", "Kevin Appasamy",        "MZE", 178.0,  95,  17},
            {"M-0302", "Ama Mensah",            "BNS", 382.0,  64,  18},
            {"M-0056", "Priya Gopal",           "POT", 215.0,  39,  19},
            {"M-0301", "Kwame Osei",            "TEA", 92.0,   91,  20},
            {"M-0306", "Akosua Addo",           "MZE", 312.0,  87,  7},
            {"M-0307", "Kwadwo Owusu",          "POT", 188.0,  76,  11},
            {"M-0308", "Esi Koomson",           "BNS", 222.0,  95,  15}
    };

    //I implemented this seeder function to instantiate delivery objects and populate season records into memory
    public static void populateInitialData(SeasonService seasonService) {
        int recordCount = 0;

        for (Object[] row : RAW_DELIVERY_DATA) {
            recordCount++;

            //I extracted each row attribute into local variables before passing them into Delivery
            String memberId = (String) row[0];
            String memberName = (String) row[1];
            String produceCode = (String) row[2];
            double mass = (double) row[3];
            int qualityScore = (int) row[4];
            int week = (int) row[5];

            //I let SeasonService create the delivery, run payout calculations, and update the season's weekly volume grid
            seasonService.addDelivery(memberId, memberName, produceCode, mass, qualityScore, week);
        }

        //I displayed a summary output confirming the total number of pre-loaded delivery records
        System.out.println("Successfully seeded " + recordCount + " initial delivery records into memory.");
    }
}