// Button 
interface Button {
    void paint();
}

// Windows Button
class WindowsButton implements Button {
    public void paint() {
        System.out.println("This is Windows button.");
    }
}

// Mac Button
class MacButton implements Button {
    public void paint() {
        System.out.println("This is Mac button.");
    }
}


// GUI Factory Button
interface GUIFactory {
    Button createButton();
}

// Windows Factory
class WindowsFactory implements GUIFactory {
    public Button createButton() {
        return new WindowsButton();
    }
}

// Mac Factory
class MacFactory implements GUIFactory {
    public Button createButton() {
        return new MacButton();
    }
}


public class FactoryMethodDemo {

    public static void main(String[] args) {
        GUIFactory factory1 = new WindowsFactory();
        Button newButton1 = factory1.createButton();
        newButton1.paint();

        GUIFactory factory2 = new MacFactory();
        Button newButton2 = factory2.createButton();
        newButton2.paint();
    }
}