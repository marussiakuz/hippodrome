import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class HorseTest {
    private static Horse horse;

    @BeforeAll
    public static void setUp() {
        horse = new Horse("Cherry", 35.5, 100500.15);
    }

    @Test
    void createHorseIfNameIsNullThenThrowsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 35.5, 100500.15);
        }, "doesn't throw IllegalArgumentException if name is null");
        assertEquals("Name cannot be null.", ex.getMessage(), "the received error message " +
                "doesn't match the expected one");
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "\t", "\n" })
    void createHorseIfNameIsBlankThenThrowsIllegalArgumentException(String name) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, 35.5, 100500.15);
        }, "doesn't throw IllegalArgumentException if name is blank");
        assertEquals("Name cannot be blank.", ex.getMessage(), "the received error message " +
                "doesn't match the expected one");
    }

    @Test
    void createHorseIfSpeedIsNegativeThenThrowsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Cherry", -0.1, 100500.15);
        }, "doesn't throw IllegalArgumentException if speed is negative");
        assertEquals("Speed cannot be negative.", ex.getMessage(), "the received error message " +
                "doesn't match the expected one");
    }

    @Test
    void createHorseIfDistanceIsNegativeThenThrowsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Cherry", 0.1, -0.15);
        }, "doesn't throw IllegalArgumentException if distance is negative");
        assertEquals("Distance cannot be negative.", ex.getMessage(), "the received error message " +
                "doesn't match the expected one");
    }

    @Test
    void getNameShouldReturnFirstArgument() {
        assertEquals("Cherry", horse.getName(), "the method getName() doesn't return name");
    }

    @Test
    void getSpeedShouldReturnSecondArgument() {
        assertEquals(35.5, horse.getSpeed(), "the method getSpeed() doesn't return speed");
    }

    @Test
    void getDistanceShouldReturnThirdArgument() {
        assertEquals(100500.15, horse.getDistance(), "the method getDistance() doesn't return distance");
    }

    @Test
    void getDistanceShouldReturnZeroIfConstructorWithTwoArguments() {
        Horse horseWithZeroDistance = new Horse("Cherry", 35.5);

        assertEquals(0, horseWithZeroDistance.getDistance(), "the method getDistance() " +
                "doesn't return 0 if using constructor with two arguments");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.5, 0.1, 15.23 })
    void moveShouldCallStaticMethodGetRandomDoubleAndAssignACertainValueToTheDistance(double value) {
        double oldDistance = horse.getDistance();

        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9))
                    .thenReturn(value);

            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));

            assertThat(horse.getDistance()).isEqualTo(oldDistance + horse.getSpeed() * value);
        }
    }
}