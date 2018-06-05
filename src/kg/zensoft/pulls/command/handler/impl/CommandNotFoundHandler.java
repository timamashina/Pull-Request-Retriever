package kg.zensoft.pulls.command.handler.impl;

import kg.zensoft.pulls.command.handler.CommandHandler;
import kg.zensoft.pulls.service.io.InputOutputService;

public class CommandNotFoundHandler implements CommandHandler {
    private InputOutputService inputOutputService;

    public CommandNotFoundHandler(InputOutputService inputOutputService) {
        this.inputOutputService = inputOutputService;
    }

    @Override
    public void handleCommand() {
        inputOutputService.printMessage("Unknown command\n");
    }
}