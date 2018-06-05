package kg.zensoft.pulls.service.json;


public interface TextDataExchangeFormatConverter {

    <T> String toString(T t);

    <T,E> T toObject(String s, Class<E> tClass);

    <T> T getAttributeValue(String s, String attributeName);
}