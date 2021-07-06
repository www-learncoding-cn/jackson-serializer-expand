package cn.learncoding.jackson.serializer.expand;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class DataMaskingJsonSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private String fullName;

    private MaskingType maskingType;

    private DataMaskingSerializer serializer;

    private int begin;

    private int end;

    private char replaceChar;

    public DataMaskingJsonSerializer() {
    }

    private DataMaskingJsonSerializer(String fullName, MaskingType maskingType, Class<? extends DataMaskingSerializer> serializerClass,
                                      int begin, int end, char replaceChar) {
        this.fullName = fullName;
        this.maskingType = maskingType;
        try {
            this.serializer = DataMaskingSerializer.class.equals(serializerClass) ? null : serializerClass.newInstance();
        } catch (Exception e) {
            log.error(fullName + " Masking annotation init error, ignore.", e);
        }
        this.begin = begin;
        this.end = end;
        this.replaceChar = replaceChar;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String writeValue = "";
        if (!MaskingType.CUSTOMER.equals(maskingType)){
            writeValue = maskingType.getDataMaskingSerializer().masking(value, replaceChar);
        }else {
            if (serializer != null){
                writeValue = serializer.masking(value, replaceChar);
            }else {
                if (begin >= 0){
                    writeValue = MaskingUtils.dealMasking(value, begin, end, replaceChar);
                }else {
                    log.warn(fullName + " Masking annotation configure error, ignore.");
                    writeValue = value == null ? "" : value.toString().trim();
                }
            }
        }
        gen.writeString(writeValue);
    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider,
                                              final BeanProperty beanProperty) throws JsonMappingException {

        if (beanProperty == null){
            return serializerProvider.findNullValueSerializer(null);
        }

        if (!Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }

        Masking masking = beanProperty.getAnnotation(Masking.class);
        if (masking == null) {
            masking = beanProperty.getContextAnnotation(Masking.class);
        }
        if (masking == null) {
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }

        return new DataMaskingJsonSerializer(beanProperty.getFullName().toString(), masking.type(),
                masking.customer(), masking.beginIndex(), masking.endIndex(), masking.replaceChar());
    }
}
