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
        // Сортировка столбцов по количеству гласных с использованием 3 подпрограмм
        // Переменная k в данном контексте отвечает за текущий индекс столбца,
        // который сравнивается с соседним столбцом в процессе сортировки массива по количеству гласных.
        // Алгоритм основан на пузырьковой сортировке, она сравнивает два соседних столбца с индексами k и k+1
        // и при необходимости меняет их местами, чтобы столбцы с меньшим количеством гласных оказались раньше.
        for (int j = 0; j < symbs.length - 1; j++) {
            for (int k = 0; k < symbs.length - 1 - j; k++) {
                int glas1 = countGlass(symbs, k, N);
                int glas2 = countGlass(symbs, k + 1, N);
                if ((glas1 > glas2) || (glas1 == glas2) && (sumAsc(symbs, k, N) > sumAsc(symbs, k + 1, N))) {
                    // если гласных букв в предыдущем столбце больше или же их одинаковое количество, но сумма ascii больше, чем в последующем, то происходит замена гласных
                    swapColonn(symbs, k, k + 1, N);
                }
            }
        }
        // 2.выводит измененный массив
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
                    freq[c - 'a']++; // Увеличиваем соответствующий счётчик
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
        int[][] Napravlenie = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        int currentNapravlenie = 0; // Текущее направление движения
        int steps = 1;    // Количество шагов в текущем сегменте
        int count = 1;    // Счётчик пройденных элементов
             // Пока не прошли все элементы массива
        while (count < N * N) {
            // Два сегмента одинаковой длины перед увеличением длины сегмента
            for (int segment = 0; segment < 2; segment++) { // Повторяем дважды для каждого направления (по два сегмента в каждом направлении)
                for (int step = 0; step < steps; step++) { // Для текущего сегмента выполняем 'steps' шагов
                    // Обновляем координаты
                    x += Napravlenie[currentNapravlenie][0];
                    y += Napravlenie[currentNapravlenie][1];

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
    // блок подпрограмм
    // делает подсчёт количества гласных в столбце
    public static int countGlass(char[][] symbs, int columm, int stroka) {
        int count = 0; // для подсчета всех гласных
        for (int i = 0; i < stroka; i++) {
            if (symbs[i][columm] == 'a' || symbs[i][columm] == 'e' || symbs[i][columm] == 'i' || symbs[i][columm] == 'o' || symbs[i][columm] == 'u' ||
                    symbs[i][columm] == 'A' || symbs[i][columm] == 'E' || symbs[i][columm] == 'I' || symbs[i][columm] == 'O' || symbs[i][columm] == 'U') {
                count++; // если символ гласная, то счетчик + 1
            }
        }
        return count;
    }

    // подсчёт суммы аски-кодов в столбце
    public static int sumAsc(char[][] symbs, int column, int stroka) {
        int sum = 0;
        for (int i = 0; i < stroka; i++) {
            sum += symbs[i][column];   //считает сумму ascii
        }
        return sum;
    }

    // перемещение букв в строках
    public static void swapColonn(char[][] symbs, int column1, int column2, int stroka) {
        for (int i = 0; i < stroka; i++) {
            char temp = symbs[i][column1];
            symbs[i][column1] = symbs[i][column2];
            symbs[i][column2] = temp;
        }
    }

    // конец первого блока подпрограмм
}
