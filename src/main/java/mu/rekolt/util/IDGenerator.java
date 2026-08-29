package mu.rekolt.util;

// We added the AtomicInteger import to ensure thread-safe ID generation across multiple threads
import java.util.concurrent.atomic.AtomicInteger;

// We added the IdGenerator utility class to manage and provide unique identifier numbers
public class IDGenerator {
    // We added a static AtomicInteger sequence initialized to 1 to track unique ID values safely
    private static final AtomicInteger sequence = new AtomicInteger(1);

    // We added the getNextId method to fetch and atomically increment the ID sequence for callers
    public static int getNextId() {
        // Our getAndIncrement call is to safely increment or increase the current ID value by 1 and return the previous value
        return sequence.getAndIncrement();
    }
}