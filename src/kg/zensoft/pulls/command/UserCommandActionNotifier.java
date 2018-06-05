package kg.zensoft.pulls.command;

import kg.zensoft.pulls.model.Command;
import kg.zensoft.pulls.service.io.InputOutputService;

public class UserCommandActionNotifier {
    private CommandReceiver commandReceiver;
    private InputOutputService inputOutputService;

    public UserCommandActionNotifier(CommandReceiver commandReceiver, InputOutputService inputOutputService) {
        this.commandReceiver = commandReceiver;
        this.inputOutputService = inputOutputService;
    }

    public UserCommandActionNotifier(InputOutputService inputOutputService) {
        this.inputOutputService = inputOutputService;
    }

    public void waitUserCommand() {
        String command = (String) inputOutputService.getUserInput();
        if (commandReceiver != null)
            commandReceiver.onReceive(Command.getByName(command));
    }

    public CommandReceiver getOnCommandReceive() {
        return commandReceiver;
    }

    public void setOnCommandReceive(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public InputOutputService getInputOutputService() {
        return inputOutputService;
    }

    public void setInputOutputService(InputOutputService inputOutputService) {
        this.inputOutputService = inputOutputService;
    }
}
