package eCommerce.com.eCommerce.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    //user
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),
    //product
    PRODUCT_READ("product:read"),
    PRODUCT_UPDATE("product:update"),
    PRODUCT_CREATE("product:create"),
    PRODUCT_DELETE("product:delete"),
    //orderDetails
    ORDER_DETAILS_READ("order:read"),
    ORDER_DETAILS_UPDATE("order:update"),
    ORDER_DETAILS_CREATE("order:create"),
    ORDER_DETAILS_DELETE("order:delete");
    //order


    @Getter
    private final String permission;


}
