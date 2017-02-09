package ESC.Week3;

/**
 * Created by HanWei on 7/2/2017.
 */
public class PizzaStoreDemo {
    public static void main(String[] args) {
        PizzaStore myStore = new PizzaStore();
        Pizza myPizza = myStore.orderPizza("cheese");

        System.out.println(myPizza.name);
    }
}
