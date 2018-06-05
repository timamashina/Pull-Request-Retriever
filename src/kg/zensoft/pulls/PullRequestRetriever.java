package kg.zensoft.pulls;

import kg.zensoft.pulls.command.*;
import kg.zensoft.pulls.command.handler.CommandHandler;
import kg.zensoft.pulls.command.handler.impl.*;
import kg.zensoft.pulls.repository.PullRequestRepository;
import kg.zensoft.pulls.repository.impl.GitServerPullRequestRepository;
import kg.zensoft.pulls.service.git.GitServerService;
import kg.zensoft.pulls.service.io.InputOutputService;
import kg.zensoft.pulls.service.io.CommandLineInterfaceInputOutputService;
import kg.zensoft.pulls.service.git.GithubServerService;
import kg.zensoft.pulls.service.json.CustomPullRequestJsonConverter;
import kg.zensoft.pulls.service.model.PullRequestService;
import kg.zensoft.pulls.service.model.PullRequestServiceImpl;

public class PullRequestRetriever implements UserInteractionService {
    private InputOutputService inputOutputService;
    private UserCommandActionNotifier commandNotifier;
    private PullRequestService pullRequestService;
    private ShowPullRequestCommandHandler showPullRequestCommandHandler;
    private HelpCommandHandler helpCommandHandler;
    private CommandNotFoundHandler commandNotFoundHandler;
    private ExitCommandHandler exitCommandHandler;
    private OpenPullRequestInBrowserCommandHandler openPullRequestInBrowserCommandHandler;
    private Boolean exit;
    private String initialRepoName;
    private String initialUsername;

    public PullRequestRetriever(String initialRepoName, String initialUsername) {
        this.initialRepoName = initialRepoName;
        this.initialUsername = initialUsername;
        initFields();
    }

    private void initFields() {
        exit = false;
        inputOutputService = new CommandLineInterfaceInputOutputService();
        GitServerService gitServerService = new GithubServerService();
        PullRequestRepository pullRequestRepository = new GitServerPullRequestRepository(gitServerService, new CustomPullRequestJsonConverter());
        pullRequestService = new PullRequestServiceImpl(pullRequestRepository);
        showPullRequestCommandHandler = new ShowPullRequestCommandHandler(pullRequestService, inputOutputService, initialUsername, initialRepoName);
        openPullRequestInBrowserCommandHandler = new OpenPullRequestInBrowserCommandHandler(pullRequestService, inputOutputService, initialUsername, initialRepoName);
        helpCommandHandler = new HelpCommandHandler(inputOutputService);
        commandNotFoundHandler = new CommandNotFoundHandler(inputOutputService);
        exitCommandHandler = new ExitCommandHandler(this);
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

    private void showWelcomeText() {
        inputOutputService.printMessage("Welcome to Pull Request Retriever!!!\n");
        inputOutputService.printMessage("This program can show or open you pull requests of a \npublic repositories in Github by a username\n");
    }

    @Override
    public void startUserInteraction() {
        commandNotifier = new UserCommandActionNotifier(inputOutputService);
        commandNotifier.setOnCommandReceive((command) -> {
            CommandHandler commandHandler = commandNotFoundHandler;
            if (command == null) {
                commandHandler.handleCommand();
                return;
            }
            switch (command) {
                case HELP:
                    commandHandler = helpCommandHandler;
                    break;
                case LIST_PULL_REQUESTS:
                    commandHandler = showPullRequestCommandHandler;
                    break;
                case OPEN_PULL_REQUESTS:
                    commandHandler = openPullRequestInBrowserCommandHandler;
                    break;
                case EXIT:
                    commandHandler = exitCommandHandler;
                    break;
            }
            commandHandler.handleCommand();
        });
        showWelcomeText();
        helpCommandHandler.handleCommand();
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
