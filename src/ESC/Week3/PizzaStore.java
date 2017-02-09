package ESC.Week3;

public class PizzaStore {

    public Pizza orderPizza(String type) {
        Pizza pizza = PizzaFactory.getPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

}

class PizzaFactory {
    public static Pizza getPizza(String type) {
        switch (type) {
            case "cheese":
                return new CheesePizza(type);
            case "greek":
                return new GreekPizza(type);
            case "pepperoni":
                return new PepperoniPizza(type);
        }
        return null;
    }
}

class Pizza {

    protected String name;

    public void prepare() {
    }

    public void box() {
    }

    public void cut() {
    }

    public void bake() {
    }
}

class CheesePizza extends Pizza {
    public CheesePizza(String n) {
        name = n;
    }
}

class GreekPizza extends Pizza {
    public GreekPizza(String n) {
        name = n;
    }
}

class PepperoniPizza extends Pizza {
    public PepperoniPizza(String n) {
        name = n;
    }
}

