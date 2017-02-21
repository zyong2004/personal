package algorithm;

import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;

/***
 * 归并排序 归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。
 * 
 * 将数组分为前后两段，first-mid mid+1 -last
 * 
 * @author zhangyong
 * 
 */
public class MergeSort {

	public static void mergeArray(int a[], int first, int mid, int last,
			int[] temp) {
		int i = first, j = mid + 1;
		int m = mid, n = last;
		int k = 0;

		while (i <= m && j <= n) {// 如果正巧排序 前半段有序，执行后且前半段的每个值都比后半段的第一个值小，只循环了i++
									// ,j并没有执行，少数据
			// 如果排序后半段有序，且后半段的每个值都比前端的小，只执行了j++，i++没有执行，少数据

			if (a[i] <= a[j]) {
				temp[k++] = a[i++];
			} else {
				temp[k++] = a[j++];
			}
		}

		while (i <= m) {//把剩下的元素也都放到临时数组中  
			int s = a[i++];
		//	System.out.println("s=="+s);
			temp[k++] = s;
		}
		while (j <= n) {
			temp[k++] = a[j++];
		}
		//System.out.println("=================" + temp.length+" k ==="+k);
		for (i = 0; i < k; i++) {//一定不要搞错，以k为范围
		//	System.out.println("for = "+ first+"-"+ i);
			a[first + i] = temp[i];
		}

	}

	public static void arraySort(int array[], int temp[], int first, int last) {
		if (first < last) {
			int middle = (first + last) >>1;
			arraySort(array, temp, first, middle);//左边有序
			arraySort(array, temp, middle + 1, last);//右边有序  
			mergeArray(array, first, middle, last, temp);
		}
	}

	public static void main(String[] args) {

		int size =200000;
		int[] a = new int[size];
		for (int i = 0; i < size; i++) {
			//System.out.println(i);
			a[i] = RandomUtils.nextInt(100) + i;
		}

	/*	for (int i = 0; i < a.length; i++) {
			System.out.print(a[i]+",");
		}*/
		System.out.println();
		long s = System.currentTimeMillis();

		int[] m = new int[size] ;
		arraySort(a, m, 0, a.length - 1);

		System.out.println(System.currentTimeMillis() -s);
		/*for (int i = 0; i < a.length; i++) {
			 System.out.print(a[i]+",");
		}*/
	}

}
