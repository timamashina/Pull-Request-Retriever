package kg.zensoft.pulls.command;

public interface Receiver<T> {
    void onReceive(T t);
}