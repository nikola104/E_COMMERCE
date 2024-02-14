package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.request.TypeRequest;
import eCommerce.com.eCommerce.model.Type;
import eCommerce.com.eCommerce.service.TypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/type")
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }
    @PostMapping("/save-type")
    public ResponseEntity<String> saveType(@RequestBody @Valid TypeRequest typeRequest){
       return new ResponseEntity<>(typeService.saveType(typeRequest), HttpStatus.CREATED);
    }
    @PutMapping("/update-type/{id}")
    public ResponseEntity<String> updateType(@PathVariable Long id, @RequestBody @Valid TypeRequest typeRequest){
        return new ResponseEntity<>(typeService.updateType(id, typeRequest), HttpStatus.OK);
    }
    @GetMapping("/get-type/{id}")
    public ResponseEntity<Type> getType(@PathVariable Long id){
        return new ResponseEntity<>(typeService.getType(id), HttpStatus.OK);
    }
    @GetMapping("/get-all-types")
    public ResponseEntity<List<Type>> getAllTypes(){
        return new ResponseEntity<>(typeService.getAllTypes(), HttpStatus.OK);
    }
    @DeleteMapping("/delete-type/{id}")
    public ResponseEntity<String> deleteType(@PathVariable Long id){
        return new ResponseEntity<>(typeService.deleteType(id), HttpStatus.OK);
    }
}
