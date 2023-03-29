import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Description
 * Date 2022/6/12
 * Created by muggle
 */

public class Eatch {

    /*public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            final String s = scanner.nextLine();
            final int i = s.lastIndexOf(" ");
            System.out.println(s.substring(i+1).length());
        }
    }*/

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            final TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>(new Comparator<Integer>() {
                public int compare(Integer o1, Integer o2) {
                    return o1-o2;
                }
            });
            final int i = scanner.nextInt();
            for (int j = 0; j < i; j++) {
                final int key = scanner.nextInt();
                final int vaule = scanner.nextInt();
                if (map.get(key)!=null){
                    map.put(key,map.get(key)+vaule);
                }else {
                    map.put(key,vaule);
                }
            }
            for(Integer j : map.keySet()){
                System.out.println(j + " " + map.get(j));
            }
        }
    }

    /*public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        int i = 2;
        while (i*i <= num) {
            if (num % i == 0) {
                System.out.print(i + " ");
                num /= i;
            } else {
                i++;
            }
        }
        System.out.println(num);
    }*/

    /*
    * 第一行两个整数n，m，其中n代表往锅里下的菜的个数，m代表手速。
    * 接下来有n行，每行有两个数x，y代表第x秒下的菜过y秒才能变得刚好合适。
    * （1 < n, m < 1000）
    * （1 < x, y < 1000）
    * */
    public static void eatfood(){
        final Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            int [] x=new int[n];
            int [] y=new int[n];
            for (int i = 0; i < n; i++) {
                x[i]=scanner.nextInt();
                y[i]=scanner.nextInt();
            }
            int [] eat=new int[n];
            for (int i = 0; i < n; i++) {
                eat[i]=x[i]+y[i];
            }
            int [] canEat=new int[n];
            int eatTime=0;
            for (int i = 0; i < n; i++) {
                if (eat[i]>=eatTime){
                    canEat[i]=1;
                    eatTime=eat[i]+m;
                }
            }
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (canEat[i]==1){
                    count++;
                }
            }
            System.out.println(count);
            scanner.close();
        }
    }

    /**
     * 第一题. 输入一个整型数组和一个正整数N，排序去重后输出这个数组中最大的N个数和最小的N个数的和，要求不允许最大的N个数和最小的N个数有重复，如果有重复就输出-1
     */
    public static void sumNum(){
        final Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            final TreeSet<Integer> integers = new TreeSet<Integer>(new Comparator<Integer>() {
                public int compare(Integer o1, Integer o2) {
                    return o1-o2;
                }
            });
            final int m = scanner.nextInt();
            final int n = scanner.nextInt();
            for (int i = 0; i < m; i++) {
                integers.add(scanner.nextInt());
            }
            int left=0;
            int right=0;

            if (integers.size()>(2*n)){
                System.out.println("-1");
            }else {
                int rLengt=integers.size()-n-1;
                final Iterator<Integer> iterator = integers.iterator();
                int i=0;
                while (iterator.hasNext()) {
                    if (i<n){
                        left=left+iterator.next();
                    }
                    if (i>rLengt){
                        right=right+iterator.next();
                    }
                    i++;
                }
                System.out.println(left+right);
            }
        }

    }


    public static void rgx(){
        Scanner scanner = new Scanner(System.in);
        String phone = scanner.next();
        boolean res;
        res =phone.matches("1[35789]\\d{9}");
        if(res == true){
            System.out.println("格式正确");
        }else {
            System.out.println("格式错误");
        }
    }
    


    /**
     * 通过交换进行插入排序，借鉴冒泡排序
     *
     * @param a
     */
    public static void sort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (a[j] < a[j - 1]) {
                    int temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                }
            }
        }
    }


    /*
    * 将待排序数组按照步长gap进行分组，然后将每组的元素利用直接插入排序的方法进行排序；
    * 每次再将gap折半减小，循环上述操作；当gap=1时，利用直接插入，完成排序。
    * */
    public static void sort3(int[] a) {
        int length = a.length;
        int h = 1;
        while (h < length / 3) h = 3 * h + 1;
        for (; h >= 1; h /= 3) {
            for (int i = 0; i < a.length - h; i += h) {
                for (int j = i + h; j > 0; j -= h) {
                    if (a[j] < a[j - h]) {
                        int temp = a[j];
                        a[j] = a[j - h];
                        a[j - h] = temp;
                    }
                }
            }
        }
    }

    public static void sort2(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int min = i;
            //选出之后待排序中值最小的位置
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            //最小值不等于当前值时进行交换
            if (min != i) {
                int temp = a[i];
                a[i] = a[min];
                a[min] = temp;
            }
        }
    }

    public static void sort(int[] a, int low, int high) {
        //已经排完
        if (low >= high) {
            return;
        }
        int left = low;
        int right = high;

        //保存基准值
        int pivot = a[left];
        while (left < right) {
            //从后向前找到比基准小的元素
            while (left < right && a[right] >= pivot)
                right--;
            a[left] = a[right];
            //从前往后找到比基准大的元素
            while (left < right && a[left] <= pivot)
                left++;
            a[right] = a[left];
        }
        // 放置基准值，准备分治递归快排
        a[left] = pivot;
        sort(a, low, left - 1);
        sort(a, left + 1, high);
    }

    public static void steTest(){
        final Scanner scanner = new Scanner(System.in);
        final String s = scanner.nextLine();
        final char[] chars = s.toCharArray();
        int [] sizeArr=new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            final char target = chars[i];
            int length=0;
            for (int j =i; j < chars.length; j++) {
                if (chars[j]==chars[i]){
                    length++;
                }else {
                    sizeArr[i]=length;
                    break;
                }
            }
        }
    }
}
