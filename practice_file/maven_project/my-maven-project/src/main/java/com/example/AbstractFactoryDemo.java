package com.example;

interface Button {
    void paint();
}

interface Checkbox {
    void paint();
}

class WinButton implements Button {
    public void paint() {
        System.out.println("Rendering a button in Windows style.");
    }
}

class MacButton implements Button {
    public void paint() {
        System.out.println("Rendering a button in MacOS style.");
    }
}

class WinCheckbox implements Checkbox {
    public void paint() {
        System.out.println("Rendering a checkbox in Windows style.");
    }
}

class MacCheckbox implements Checkbox {
    public void paint() {
        System.out.println("Rendering a checkbox in MacOS style.");
    }
}

interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

class WinFactory implements GUIFactory {
    public Button createButton() {
        return new WinButton();
    }
    
    public Checkbox createCheckbox() {
        return new WinCheckbox();
    }
}

class MacFactory implements GUIFactory {
    public Button createButton() {
        return new MacButton();
    }
    
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}

public class AbstractFactoryDemo {
    public static void main(String[] args) {
        GUIFactory factory = new MacFactory();
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        button.paint();
        checkbox.paint();
    }
}
