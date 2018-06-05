package kg.zensoft.pulls.command.handler.impl;

import kg.zensoft.pulls.command.handler.CommandHandler;
import kg.zensoft.pulls.model.Command;
import kg.zensoft.pulls.service.io.InputOutputService;

public class HelpCommandHandler implements CommandHandler {
    private InputOutputService inputOutputService;

    public HelpCommandHandler(InputOutputService inputOutputService) {
        this.inputOutputService = inputOutputService;
    }

    @Override
    public void handleCommand() {
        inputOutputService.printMessage("Available commands: \n");
        int count = 1;
        for (Command command : Command.values()) {
            inputOutputService.printMessage(count + ". " + command.getName() + " - " + command.getDescription() + "\n");
            count++;
        }
    }
}