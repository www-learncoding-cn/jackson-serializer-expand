package cn.learncoding.jackson.serializer.expand;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class FormatJsonSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private String fullName;

    private String format;

    public FormatJsonSerializer() {
    }

    private FormatJsonSerializer(String fullName, String format) {
        this.fullName = fullName;
        this.format = format;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            String writeValue = String.format(format, value);
            gen.writeString(writeValue);
        }catch (Exception e) {
            log.error(String.format("%s use %s value value %s error.", fullName, format, value), e);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider,
                                              final BeanProperty beanProperty) throws JsonMappingException {

        if (beanProperty == null){
            return serializerProvider.findNullValueSerializer(null);
        }

        Class<?> clazz = beanProperty.getType().getRawClass();
        if (!(CharSequence.class.isAssignableFrom(clazz)
                || Number.class.isAssignableFrom(clazz))) {
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }

        Format format = beanProperty.getAnnotation(Format.class);
        if (format == null) {
            format = beanProperty.getContextAnnotation(Format.class);
        }
        if (format == null) {
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }

        return new FormatJsonSerializer(beanProperty.getFullName().toString(), format.value());
    }
}
