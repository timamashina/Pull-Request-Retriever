package kg.zensoft.pulls.service.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonConverter implements TextDataExchangeFormatConverter {
    @Override
    public <T> String toString(T t) {
        return null;
    }

    @Override
    public <T, E> T toObject(String s, Class<E> tClass) {
        try {
            E e = tClass.newInstance();
            for (Field field : tClass.getFields()) {
                JsonConverterConfig jsonParseConfig = field.getAnnotation(JsonConverterConfig.class);
                String jsonFieldName = field.getName();
                if (jsonParseConfig != null) {
                    jsonFieldName = jsonParseConfig.replaceWith();
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getAttributeValue(String json, String attributeName) {
        return null;
    }
}
