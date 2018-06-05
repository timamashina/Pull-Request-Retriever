package kg.zensoft.pulls.service.io;

import java.util.Scanner;

public class CommandLineInterfaceInputOutputService implements InputOutputService<String> {
    private Scanner scanner;


    public CommandLineInterfaceInputOutputService() {
        scanner = new Scanner(System.in);
    }

    public CommandLineInterfaceInputOutputService(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }

    @Override
    public void printMessage(String message) {
        System.out.print(message);
    }
}