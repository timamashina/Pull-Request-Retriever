package kg.zensoft.pulls.service.git;

public class InvalidUsernameOrRepositoryNameException extends Exception {
    public InvalidUsernameOrRepositoryNameException() {
    }

    public InvalidUsernameOrRepositoryNameException(String s) {
        super(s);
    }
}
