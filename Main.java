import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // Считывание массива
        System.out.println("Введите размер массива N:");
        int N = in.nextInt();
        char[][] symbs = new char[N][N];
        // 1.считывает с консоли размер массива N, затем элементы массива
        // размером N*N.
        System.out.println("Введите элементы массива построчно через пробел, в каждой строке по N элементов:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                symbs[i][j] = in.next().charAt(0);
            }
        }
        // Сортировка столбцов по количеству гласных
        // Переменная k в данном контексте отвечает за текущий индекс столбца,
        // который сравнивается с соседним столбцом в процессе сортировки массива по количеству гласных.
        // Алгоритм основан на пузырьковой сортировке, она сравнивает два соседних столбца с индексами k и k+1
        // и при необходимости меняет их местами, чтобы столбцы с меньшим количеством гласных оказались раньше.
        for (int j = 0; j < N - 1; j++) {
            for (int k = 0; k < N - 1 - j; k++) {

                // Подсчет гласных в столбце k
                int glas1 = 0;
                for (int i = 0; i < N; i++) {
                    char character = symbs[i][k];
                    if ("aAeEiIoOuUyY".indexOf(character) >= 0) {
                        glas1++;
                    }
                }

                // Подсчет гласных в столбце k+1
                int glas2 = 0;
                for (int i = 0; i < N; i++) {
                    char ch = symbs[i][k + 1];
                    if ("aAeEiIoOuUy".indexOf(ch) >= 0) {
                        glas2++;
                    }
                }

                // Подсчет суммы ASCII в столбце k
                int sum1 = 0;
                for (int i = 0; i < N; i++) {
                    sum1 += symbs[i][k];
                }

                // Подсчет суммы ASCII в столбце k+1
                int sum2 = 0;
                for (int i = 0; i < N; i++) {
                    sum2 += symbs[i][k + 1];
                }

                // Сравнение и замена столбцов
                if ((glas1 > glas2) || (glas1 == glas2 && sum1 > sum2)) {
                    for (int i = 0; i < N; i++) {
                        char temp = symbs[i][k];
                        symbs[i][k] = symbs[i][k + 1];
                        symbs[i][k + 1] = temp;
                    }
                }
            }
        }

        // Вывод измененного массива
        System.out.println("Отсортированный массив:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(symbs[i][j] + " ");
            }
            System.out.println();
        }
        // 3. нахождение самого частого элемента (без подрограмм)
        int q = 'z' - 'a' + 1; // удобная переменная для знания количества букв в нижнем регистре
        int[] freq = new int[q]; // Массив для подсчёта частот букв
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                char c = symbs[i][j];
                // Проверяем, является ли символ буквой верхнего или нижнего регистра
                if (c >= 'A' && c <= 'Z') { // Если символ в верхнем регистре
                    c = (char) (c + 'a' - 'A'); // Преобразуем символ в нижний регистр
                }
                if (c >= 'a' && c <= 'z') { // Если символ - буква нижнего регистра
                    freq[c - 'a']++; // У еличиваем соответствующий счётчик
                }
            }
        }
// поиск количества самого частого элемента
        int maxF = 0; // переменная, обозначающая счетчик, который считает количество каждого элемента и затем отбирает лишь наибольший
        for (int i = 0; i < freq.length; i++) {
            int FFreq = freq[i];
            if (FFreq > maxF) {
                maxF = FFreq;
            }
        }
// Поиск всех символов с максимальной частотой
        System.out.print("Символ(ы), которые встречаются чаще всего: ");
        for (int i = 0; i < q; i++) {
            if (freq[i] == maxF) {
                System.out.print((char) (i + 'a') + " "); // Преобразуем индекс обратно в символ
            }
        }


        // 4. спираль (это ужас)
        System.out.println("\nЭлементы массива в виде спирали (против часовой стрелки):");
        // Начальные координаты для центра
        int x = N / 2;
        int y = N / 2;
        System.out.print(symbs[x][y] + " "); // Центральный элемент
        // Направления движения: вверх, влево, вниз, вправо
        int[][] napravlenie  = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        int currentNapravlenie = 0; // Текущее направление движения
        int steps = 1;    // Количество шагов в текущем сегменте
        int count = 1;    // Счётчик пройденных элементов
        // Пока не прошли все элементы массива
        while (count < N * N) {
            // Два сегмента одинаковой длины перед увеличением длины сегмента
            for (int segment = 0; segment < 2; segment++) { // Повторяем дважды для каждого направления (по два сегмента в каждом направлении)
                for (int step = 0; step < steps; step++) { // Для текущего сегмента выполняем 'steps' шагов
                    // Обновляем координаты
                    x += napravlenie[currentNapravlenie][0];
                    y += napravlenie[currentNapravlenie][1];

                    // Проверка на выход за пределы массива
                    if (x >= 0 && x < N && y >= 0 && y < N) {
                        System.out.print(symbs[x][y] + " ");
                        count++;
                    }
                }
                // После завершения одного сегмента (например, движения вверх или влево), меняем направление
                // так как у нас 4 направления, то после прохождения всех, то мы прибавляем к текущему направлению один и находим остаток от деления на 4
                currentNapravlenie = (currentNapravlenie + 1) % 4;
            }
            // Увеличиваем количество шагов для следующей пары сегментов
            steps++;
        }
        // 5. зашифрованный массив
        System.out.println("\nЗашифрованный массив:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (Character.isUpperCase(symbs[i][j])) { // Если буква заглавная, делаем её строчной
                    symbs[i][j] = Character.toLowerCase(symbs[i][j]);
                } else if (Character.isLowerCase(symbs[i][j])) { // Если буква строчная, делаем её заглавной
                    symbs[i][j] = Character.toUpperCase(symbs[i][j]);
                }
                System.out.print(symbs[i][j] + " "); // Вывод символа
            }
            System.out.println(); // Переход на новую строку после каждого ряда
        }
    }
}
