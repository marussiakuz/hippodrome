import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MainTest {

    @Test
    @Disabled
    @Timeout(value = 22)
    void mainShouldBeExecutedInTwentyTwoSecondsOtherwiseFails() throws Exception {
        Main.main(null);
    }
}