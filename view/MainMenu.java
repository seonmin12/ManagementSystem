package view;

import controller.Controller;
import controller.ControllerImpl;

import java.util.Scanner;

public class MainMenu {
    private Controller control;
    private Scanner scan;

    public MainMenu() {
        control = ControllerImpl.getInstance();
        scan = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new MainMenu().managementStart();
    }

    public void managementStart(){
        // 시작
    }

}
