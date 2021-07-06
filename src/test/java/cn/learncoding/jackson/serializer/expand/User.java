package cn.learncoding.jackson.serializer.expand;

import lombok.Data;

@Data
public class User {

    private Long id;

    @Masking(type = MaskingType.CUSTOMER, beginIndex = 4, endIndex = -4)
    private String username;

    @Masking(type = MaskingType.NAME)
    private String name;

    @Masking(type = MaskingType.PHONE)
    private String phone;

    @Masking(type = MaskingType.ID_CARD)
    private String idCard;

    @Masking(type = MaskingType.EMAIL)
    private String email;

    @Format("%s_100x100")
    private String imageUrl;

    @Format("%.2f")
    private Float money;
}