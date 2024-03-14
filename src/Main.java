// Вариант 2
// В последовательности чисел найти сумму «чётных и отрицательных» и «нечётных и
// отрицательных» чисел. но чтобы при этом в описании
// требуемого класса учитывались указанные исключительные ситуации:
// Строка содержит некоторый символ, Равенство некоторому числу, В строке есть литеры
//

import java.util.Scanner;

//// Интерфейс для работы с последовательностью целых чисел
interface NumberSequence {
    static final int BANNED_NUMBER = 100; // это число не должно присутствовать

    int[] getSequence() throws NumberEqualityException, CharacterFoundException, SpecificCharacterFoundException;
}

//// Интерфейс для выполнения задачи
interface TaskSolver {
    void solveTask(int[] sequence);
}

//// Исключение, выбрасываемое при вводе числа, которое равно заданному числу
class NumberEqualityException extends Exception {
    public NumberEqualityException(String message) {
        super(message);
    }
}

//// Исключение, выбрасываемое при присутствии литер в строке
class CharacterFoundException extends Exception {
    public CharacterFoundException(String message) {
        super(message);
    }
}

//// Исключение, выбрасываемое при наличии конкретного символа в строке
class SpecificCharacterFoundException extends Exception {
    public SpecificCharacterFoundException(String message) {
        super(message);
    }
}

//// Основной класс, реализующий интерфейсы
class SequenceProcessor implements NumberSequence, TaskSolver {
    private int[] sequence;

    @Override
    public int[] getSequence() throws NumberEqualityException, CharacterFoundException, SpecificCharacterFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите элементы последовательности в одну строку через пробел:");
        String input = scanner.nextLine();
        if (input.contains("[")) {
            throw new SpecificCharacterFoundException("Найден символ [");
        }
        String[] numbersAsString = input.split(" ");
        sequence = new int[numbersAsString.length];
        for (int i = 0; i < numbersAsString.length; i++) {
            try {
                sequence[i] = Integer.parseInt(numbersAsString[i]);
                if (sequence[i] == NumberSequence.BANNED_NUMBER) {
                    throw new NumberEqualityException("Введено число равное запрещенному числу: "
                            + NumberSequence.BANNED_NUMBER);
                }
            } catch (NumberFormatException e) {
                throw new CharacterFoundException("В строке найдена литера");
            }
        }
        scanner.close();
        return sequence;
    }

    @Override
    public void solveTask(int[] sequence) {
        int evenPositiveCount = 0;
        int oddPositiveCount = 0;
        for (int num : sequence) {
            if (num < 0) {
                if (num % 2 == 0) {
                    evenPositiveCount += num;
                } else {
                    oddPositiveCount += num;
                }
            }
        }
        System.out.println("Сумма четных и отрицательных: " + evenPositiveCount);
        System.out.println("Сумма нечетных и отрицательных чисел больше: " + oddPositiveCount);
    }
}


public class Main {
    public static void main(String[] args) {
        try {
            SequenceProcessor processor = new SequenceProcessor();
            int[] sequence = processor.getSequence();
            processor.solveTask(sequence);
        } catch (NumberEqualityException | CharacterFoundException | SpecificCharacterFoundException e) {
            System.out.println(e);
        }
    }
}

