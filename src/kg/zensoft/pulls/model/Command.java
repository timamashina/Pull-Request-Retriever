package kg.zensoft.pulls.model;

public enum Command {
    HELP,
    LIST_PULL_REQUESTS,
    OPEN_PULL_REQUESTS,
    EXIT;

    public static Command getByName(String name) {
        if (name.equalsIgnoreCase("help")) {
            return HELP;
        }
        if (name.startsWith("prs")) {
            if (name.contains("open")) {
                return OPEN_PULL_REQUESTS;
            }
            return LIST_PULL_REQUESTS;
        }
        if (name.equalsIgnoreCase("exit")) {
            return EXIT;
        }
        return null;
    }

    public String getName() {
        if (this.equals(HELP)) {
            return "help";
        }
        if (this.equals(LIST_PULL_REQUESTS)) {
            return "prs";
        }
        if (this.equals(OPEN_PULL_REQUESTS)) {
            return "prs open";
        }
        if (this.equals(EXIT)) {
            return "exit";
        }
        return "";
    }

    public String getDescription() {
        if (this.equals(HELP)) {
            return "Instructions";
        }
        if (this.equals(LIST_PULL_REQUESTS)) {
            return "Lists pull requests links";
        }
        if (this.equals(OPEN_PULL_REQUESTS)) {
            return "Opens pull requests links in default browser";
        }
        if (this.equals(EXIT)) {
            return "Exits the program";
        }
        return "";
    }
}