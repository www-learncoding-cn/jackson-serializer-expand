package cn.learncoding.jackson.serializer.expand;

public class MaskingUtils {

    public static String dealMasking(Object value, int begin, int end, char replaceChar) {
        if (value == null){
            return "";
        }
        String trim = value.toString().trim();
        int length = trim.length();
        if (length < begin){
            return trim;
        }
        int len;
        if (end > 0){
            len = length < end ? length : end;
        }else {
            len = length + end;
        }
        return dealReplace(trim, begin, len, replaceChar);
    }


    public static String dealReplace(String string, int begin, int len, char replaceChar) {
        char[] chars = string.toCharArray();
        for (int i = begin; i < len; i++) {
            chars[i] = replaceChar;
        }
        return new String(chars);
    }
}
