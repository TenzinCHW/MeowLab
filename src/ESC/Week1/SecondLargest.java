package ESC.Week1;

/**
 * Created by HanWei on 26/1/2017.
 */
public class SecondLargest {
    public static void main(String[] args) {
        int[] meow = {1,2,3,4};
        System.out.println(secondLargest(meow));
    }

    private static int secondLargest(int[] list) {
        int largest = Integer.max(list[0], list[1]);
        int second = Integer.min(list[0], list[1]);

        for (int i = 2; i < list.length; i++) {
            if (list[i] > largest) {
                second = largest;
                largest = list[i];
            } else if (list[i] > second) {
                second = list[i];
            }
        }

        return second;
    }
}
