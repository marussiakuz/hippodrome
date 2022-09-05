import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Test
    void createHippodromeIfListOfHorsesIsNullThenThrowsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        }, "doesn't throw IllegalArgumentException if list of horses is null");
        assertEquals("Horses cannot be null.", ex.getMessage(), "the received error message " +
                "doesn't match the expected one");
    }

    @Test
    void createHippodromeIfListOfHorsesIsEmptyThenThrowsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<>());
        }, "doesn't throw IllegalArgumentException if list of horses is empty");
        assertEquals("Horses cannot be empty.", ex.getMessage(), "the received error message " +
                "doesn't match the expected one");
    }

    @Test
    void getHorsesShouldReturnListContainingTheSameObjectsAndInTheSameSequenceAsListPassedToConstructor() {
        Hippodrome hippodrome = new Hippodrome(createListOfHorses(30));

        assertEquals(30, hippodrome.getHorses().size(), "the number in the list doesn't match " +
                "the expected one");

        assertIterableEquals(createListOfHorses(30), hippodrome.getHorses(), "the list of horses " +
                "received doesn't match the expected one");
    }

    @Test
    void moveShouldCallMoveOnAllHorses() {
        Hippodrome hippodromeWithHorseMock = new Hippodrome(IntStream.range(0, 50)
                .mapToObj(time -> Mockito.mock(Horse.class))
                .collect(Collectors.toList()));

        hippodromeWithHorseMock.move();

        assertEquals(50, hippodromeWithHorseMock.getHorses().stream()
                .peek(horse ->
                        Mockito.verify(horse, Mockito.times(1)
                                .description("horse didn't have the move() method called")).move())
                .count(), "the method move() wasn't called the required number of times");
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "30, 0", "100, 15"})
    void getWinnerShouldReturnTheHorseWithTheLargestDistanceValue(int count, int index) {
        Horse winningHorse = new Horse("Winner", 55.0, Double.MAX_VALUE);

        List<Horse> horses = createListOfHorses(count);
        horses.add(index, winningHorse);
        Hippodrome hippodromeWithWinner = new Hippodrome(horses);

        assertSame(winningHorse, hippodromeWithWinner.getWinner(),
                "doesn't return the horse with the largest distance value");
    }

    private static List<Horse> createListOfHorses(int count) {
        List<Horse> horses = new ArrayList<>();

        IntStream.range(0, count)
                .forEach(index -> {
                    Horse horse = new Horse("Horse number " + (index + 1), 25.0 + index,
                            100500.0 + index);
                    horses.add(horse);
                });

        return horses;
    }
}