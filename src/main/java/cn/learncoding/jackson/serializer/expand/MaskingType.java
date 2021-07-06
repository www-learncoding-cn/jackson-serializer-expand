package cn.learncoding.jackson.serializer.expand;

/**
 * 常见数据脱敏
 */
public enum MaskingType {
    /**
     * 手机号 默认 138****1234
     */
    PHONE(3, 7, new DataMaskingSerializer() {
        @Override
        public String masking(Object value, char replaceChar) {
            return MaskingUtils.dealMasking(value, PHONE.begin, PHONE.end, replaceChar);
        }
    }),
    /**
     * 姓名 默认 张*，张**
     */
    NAME(1, 0, new DataMaskingSerializer(){
        @Override
        public String masking(Object value, char replaceChar) {
            return MaskingUtils.dealMasking(value, NAME.begin, NAME.end, replaceChar);
        }
    }),
    /**
     * 身份证 默认 6101**********3315
     */
    ID_CARD(4, -4, new DataMaskingSerializer(){
        @Override
        public String masking(Object value, char replaceChar) {
            return MaskingUtils.dealMasking(value, ID_CARD.begin, ID_CARD.end, replaceChar);
        }
    }),
    /**
     * 银行卡 默认 **********3315
     */
    BANK_CARD(0, -4, new DataMaskingSerializer(){
        @Override
        public String masking(Object value, char replaceChar) {
            return MaskingUtils.dealMasking(value, ID_CARD.begin, ID_CARD.end, replaceChar);
        }
    }),
    /**
     * 邮箱 默认 1***@qq.com
     */
    EMAIL(1, 0, new DataMaskingSerializer() {
        @Override
        public String masking(Object value, char replaceChar) {
            if (value == null){
                return "";
            }
            String trim = value.toString().trim();
            int end = trim.indexOf("@");
            int begin = EMAIL.begin;
            if (end <= begin){
                return trim;
            }
            return MaskingUtils.dealReplace(trim, begin, end, replaceChar);
        }
    }),
    CUSTOMER;

    /**
     * 开始下标（包含）
     */
    private int begin;
    /**
     * 截止下标（不包含）
     *
     * 0 ：表示结尾，即全部替换；
     * 负数：表示从结尾倒数；
     */
    private int end;

    private DataMaskingSerializer dataMaskingSerializer;

    MaskingType() {
    }

    MaskingType(int begin, int end, DataMaskingSerializer dataMaskingSerializer) {
       this.begin = begin;
       this.end = end;
       this.dataMaskingSerializer = dataMaskingSerializer;
    }

    public DataMaskingSerializer getDataMaskingSerializer() {
        return dataMaskingSerializer;
    }
}
