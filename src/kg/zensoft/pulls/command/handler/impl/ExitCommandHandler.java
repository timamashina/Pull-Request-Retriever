package kg.zensoft.pulls.command.handler.impl;

import kg.zensoft.pulls.command.handler.CommandHandler;
import kg.zensoft.pulls.command.UserInteractionService;

public class ExitCommandHandler implements CommandHandler {

    private UserInteractionService interactionService;

    public ExitCommandHandler(UserInteractionService interactionService) {
        this.interactionService = interactionService;
    }

    @Override
    public void handleCommand() {
        interactionService.endUserInteraction();
    }
}
