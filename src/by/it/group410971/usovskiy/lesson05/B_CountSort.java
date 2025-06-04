package by.it.group410971.usovskiy.lesson05;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Первая строка содержит число 1<=n<=10000, вторая - n натуральных чисел, не превышающих 10.
Выведите упорядоченную по неубыванию последовательность этих чисел.

При сортировке реализуйте метод со сложностью O(n)

Пример: https://karussell.wordpress.com/2010/03/01/fast-integer-sorting-algorithm-on/
Вольный перевод: http://programador.ru/sorting-positive-int-linear-time/
*/

public class B_CountSort {


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = B_CountSort.class.getResourceAsStream("dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result = instance.countSort(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }

    int[] countSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        //размер массива
        int n = scanner.nextInt();
        int[] points = new int[n];

        //читаем входной массив
        for (int i = 0; i < n; i++) {
            points[i] = scanner.nextInt();
        }

        //максимально возможное значение элемента (по условию — не больше 10)
        int maxValue = 10;

        //массив подсчёта (считаем сколько раз встречается каждое число от 0 до maxValue)
        int[] count = new int[maxValue + 1]; // индексы 0..10

        for (int point : points) {
            count[point]++;
        }

        //собираем отсортированный массив обратно
        int index = 0;
        for (int i = 0; i <= maxValue; i++) {
            for (int j = 0; j < count[i]; j++) {
                points[index++] = i;
            }
        }

        return points;
    }
}
