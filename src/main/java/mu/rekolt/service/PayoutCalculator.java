package mu.rekolt.service;

import mu.rekolt.model.*;
import java.util.List;

// PayoutCalculator class handles individual delivery payout logic and print processing
public class PayoutCalculator {

    // I defined constant declarations for financial rates
    private static final double COMMISSION_RATE = 0.05;
    private static final double TRANSPORT_LEVY_PER_KG = 2.0;