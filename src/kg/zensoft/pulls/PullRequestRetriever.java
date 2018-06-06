package kg.zensoft.pulls;

import kg.zensoft.pulls.command.*;
import kg.zensoft.pulls.command.handler.CommandHandler;
import kg.zensoft.pulls.command.handler.impl.*;
import kg.zensoft.pulls.model.Command;
import kg.zensoft.pulls.repository.PullRequestRepository;
import kg.zensoft.pulls.repository.impl.GitServerPullRequestRepository;
import kg.zensoft.pulls.service.git.GitServerService;
import kg.zensoft.pulls.service.io.InputOutputService;
import kg.zensoft.pulls.service.io.CommandLineInterfaceInputOutputService;
import kg.zensoft.pulls.service.git.GithubServerService;
import kg.zensoft.pulls.service.json.CustomPullRequestJsonConverter;
import kg.zensoft.pulls.service.model.PullRequestService;
import kg.zensoft.pulls.service.model.PullRequestServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class PullRequestRetriever implements UserInteractionService {
    private InputOutputService inputOutputService;
    private UserCommandActionNotifier commandNotifier;
    private PullRequestService pullRequestService;
    private Map<Command, CommandHandler> commandHandlerMap;
    private Boolean exit;
    private String initialRepoName;
    private String initialUsername;

    public PullRequestRetriever(String initialRepoName, String initialUsername) {
        this.initialRepoName = initialRepoName;
        this.initialUsername = initialUsername;
        initFields();
        initCommandHandlers();
    }

    public static void main(String[] args) {
        String initialRepoName;
        String initialUserName;
        try {
            initialRepoName = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            initialRepoName = "";
        }
        try {
            initialUserName = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            initialUserName = "";
        }
        PullRequestRetriever pullRequestRetriever = new PullRequestRetriever(initialRepoName, initialUserName);
        pullRequestRetriever.startUserInteraction();
    }

    private void initCommandHandlers() {
        ShowPullRequestCommandHandler showPullRequestCommandHandler = new ShowPullRequestCommandHandler(pullRequestService, inputOutputService, initialUsername, initialRepoName);
        OpenPullRequestInBrowserCommandHandler openPullRequestInBrowserCommandHandler = new OpenPullRequestInBrowserCommandHandler(pullRequestService, inputOutputService, initialUsername, initialRepoName);
        HelpCommandHandler helpCommandHandler = new HelpCommandHandler(inputOutputService);
        CommandNotFoundHandler commandNotFoundHandler = new CommandNotFoundHandler(inputOutputService);
        ExitCommandHandler exitCommandHandler = new ExitCommandHandler(this);
        commandHandlerMap.put(Command.EXIT, exitCommandHandler);
        commandHandlerMap.put(Command.HELP, helpCommandHandler);
        commandHandlerMap.put(Command.LIST_PULL_REQUESTS, showPullRequestCommandHandler);
        commandHandlerMap.put(Command.OPEN_PULL_REQUESTS, openPullRequestInBrowserCommandHandler);
        commandHandlerMap.put(null, commandNotFoundHandler);
    }

    private void initFields() {
        exit = false;
        inputOutputService = new CommandLineInterfaceInputOutputService();
        GitServerService gitServerService = new GithubServerService();
        PullRequestRepository pullRequestRepository = new GitServerPullRequestRepository(gitServerService, new CustomPullRequestJsonConverter());
        pullRequestService = new PullRequestServiceImpl(pullRequestRepository);
        commandHandlerMap = new HashMap<>();
    }


    private void showWelcomeText() {
        inputOutputService.printMessage("Welcome to Pull Request Retriever!!!\n");
        inputOutputService.printMessage("This program can show or open you pull requests of a \npublic repositories in Github by a username\n");
    }

    @Override
    public void startUserInteraction() {
        commandNotifier = new UserCommandActionNotifier(inputOutputService);
        commandNotifier.setOnCommandReceive((command) -> {
            CommandHandler commandHandler = commandHandlerMap.get(command);
            commandHandler.handleCommand();
        });
        showWelcomeText();
        commandHandlerMap.get(Command.HELP).handleCommand();
        while (!exit) {
            inputOutputService.printMessage(">");
            commandNotifier.waitUserCommand();
        }
    }

    @Override
    public void endUserInteraction() {
        this.exit = true;
    }

    public String getInitialRepoName() {
        return initialRepoName;
    }

    public void setInitialRepoName(String initialRepoName) {
        this.initialRepoName = initialRepoName;
    }

    public String getInitialUsername() {
        return initialUsername;
    }

    public void setInitialUsername(String initialUsername) {
        this.initialUsername = initialUsername;
    }
}
