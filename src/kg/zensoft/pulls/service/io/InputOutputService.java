package kg.zensoft.pulls.service.io;

public interface InputOutputService<T> {

    T getUserInput();

    void printMessage(T message);
}