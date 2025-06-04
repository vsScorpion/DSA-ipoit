package by.it.group410971.usovskiy.lesson05;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Видеорегистраторы и площадь 2.
Условие то же что и в задаче А.

        По сравнению с задачей A доработайте алгоритм так, чтобы
        1) он оптимально использовал время и память:
            - за стек отвечает элиминация хвостовой рекурсии
            - за сам массив отрезков - сортировка на месте
            - рекурсивные вызовы должны проводиться на основе 3-разбиения

        2) при поиске подходящих отрезков для точки реализуйте метод бинарного поиска
        для первого отрезка решения, а затем найдите оставшуюся часть решения
        (т.е. отрезков, подходящих для точки, может быть много)

    Sample Input:
    2 3
    0 5
    7 10
    1 6 11
    Sample Output:
    1 0 0

*/


public class C_QSortOptimized {

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_QSortOptimized.class.getResourceAsStream("dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result = instance.getAccessory2(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }

    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Сортировка отрезков по началу
        quickSort(segments, 0, segments.length - 1);

        // Для каждой точки считаем, сколько отрезков ее покрывают
        for (int i = 0; i < m; i++) {
            int point = points[i];
            result[i] = countSegments(segments, point);
        }

        return result;
    }

    // Быстрая сортировка с 3-разбиением и элиминацией хвостовой рекурсии
    private void quickSort(Segment[] a, int left, int right) {
        while (left < right) {
            int lt = left, gt = right;
            Segment pivot = a[left];
            int i = left + 1;
            while (i <= gt) {
                int cmp = a[i].compareTo(pivot);
                if (cmp < 0) swap(a, lt++, i++);
                else if (cmp > 0) swap(a, i, gt--);
                else i++;
            }
            if ((lt - left) < (right - gt)) {
                quickSort(a, left, lt - 1);
                left = gt + 1; // хвостовая рекурсия
            } else {
                quickSort(a, gt + 1, right);
                right = lt - 1; // хвостовая рекурсия
            }
        }
    }

    private void swap(Segment[] a, int i, int j) {
        Segment temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // Бинарный поиск и подсчет покрывающих отрезков
    private int countSegments(Segment[] segments, int point) {
        int left = 0, right = segments.length - 1;
        int found = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (segments[mid].start <= point && segments[mid].stop >= point) {
                found = mid;
                break;
            }
            if (segments[mid].start > point) right = mid - 1;
            else left = mid + 1;
        }

        if (found == -1) return 0;

        int count = 1;
        for (int i = found - 1; i >= 0 && segments[i].stop >= point; i--) {
            if (segments[i].start <= point) count++;
        }
        for (int i = found + 1; i < segments.length && segments[i].start <= point; i++) {
            if (segments[i].stop >= point) count++;
        }
        return count;
    }

    //отрезок
    private class Segment implements Comparable {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Object o) {
            Segment other = (Segment) o;
            return Integer.compare(this.start, other.start);
        }
    }

}
