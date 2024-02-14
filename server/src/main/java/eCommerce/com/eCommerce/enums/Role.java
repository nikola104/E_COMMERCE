package eCommerce.com.eCommerce.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static eCommerce.com.eCommerce.enums.Permission.*;


@RequiredArgsConstructor

public enum Role {
    CUSTOMER(
            Set.of(
                    PRODUCT_READ,
                    ORDER_DETAILS_READ
            )

    ),
    ADMIN(
            Set.of(
                    USER_READ,
                    USER_UPDATE,
                    USER_DELETE,
                    PRODUCT_READ,
                    PRODUCT_UPDATE,
                    PRODUCT_CREATE,
                    PRODUCT_DELETE,
                    ORDER_DETAILS_READ,
                    ORDER_DETAILS_UPDATE,
                    ORDER_DETAILS_CREATE,
                    ORDER_DETAILS_DELETE
            )
    );
    @Getter
    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }

}
