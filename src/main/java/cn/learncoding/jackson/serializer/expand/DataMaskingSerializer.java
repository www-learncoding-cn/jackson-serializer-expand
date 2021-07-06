package cn.learncoding.jackson.serializer.expand;

public interface DataMaskingSerializer {

    default String masking(Object value, char replaceChar){
        return value == null ? "" : value.toString();
    }
}
