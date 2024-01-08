package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.dto.request.TypeRequest;
import eCommerce.com.eCommerce.exception.DuplicateValueException;
import eCommerce.com.eCommerce.exception.TypeNotFoundException;
import eCommerce.com.eCommerce.model.Type;
import eCommerce.com.eCommerce.repository.TypeRepository;
import eCommerce.com.eCommerce.service.SubcategoryService;
import eCommerce.com.eCommerce.service.TypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    private final SubcategoryService subcategoryService;

    public TypeServiceImpl(TypeRepository typeRepository, SubcategoryService subcategoryService) {
        this.typeRepository = typeRepository;
        this.subcategoryService = subcategoryService;
    }

    @Override
    public String saveType(TypeRequest typeRequest) {
        var typesList = typeRepository.findAll();
        for(Type type : typesList){
            if(type.getName().equals(typeRequest.getName())){
                throw new DuplicateValueException("This type already exists!");
            }
        }
        var subcategory = subcategoryService.getSubcategory(typeRequest.getSubcategoryId());
        var type = Type.builder()
                .name(typeRequest.getName())
                .subcategory(subcategory)
                .build();
        typeRepository.save(type);
        return "Successfully saved type!";
    }

    @Override
    public String updateType(Long id, TypeRequest typeRequest) {
        var type = typeRepository.findById(id).orElseThrow(() -> new TypeNotFoundException("Type not found!"));
        type.setName(typeRequest.getName());
        typeRepository.save(type);
        return "Successfully updated type!";
    }

    @Override
    public Type getType(Long id) {
        var type = typeRepository.findById(id).orElseThrow(() -> new TypeNotFoundException("Type not found!"));
        return type;
    }

    @Override
    public List<Type> getAllTypes() {
        var typesList = typeRepository.findAll();
        if(typesList.isEmpty()){
            throw new TypeNotFoundException("No types found!");
        }
        return typesList;
    }

    @Override
    public String deleteType(Long id) {
        var type = typeRepository.findById(id).orElseThrow(() -> new TypeNotFoundException("Type not found!"));
        typeRepository.delete(type);
        return "Successfully deleted type!";
    }
}
