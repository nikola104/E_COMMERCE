package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.request.TypeRequest;
import eCommerce.com.eCommerce.model.Type;

import java.util.List;

public interface TypeService{
    String saveType(TypeRequest typeRequest);

    String updateType(Long id, TypeRequest typeRequest);

    Type getType(Long id);

    List<Type> getAllTypes();

    String deleteType(Long id);
}
