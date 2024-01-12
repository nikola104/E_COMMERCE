package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.model.Inventory;
import eCommerce.com.eCommerce.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    @GetMapping("/get-inventory-by-product-id/{id}")
    public ResponseEntity<Long> getInventoryByProductId(@PathVariable Long id){
        return new ResponseEntity<>(inventoryService.getInventoryById(id), HttpStatus.OK);
    }
    @PutMapping("/update-inventory-by-id/{id}")
    public ResponseEntity<String> updateInventoryByProductId(@PathVariable Long id,@RequestBody Inventory inventory){

        return new ResponseEntity<>(inventoryService.updateInventoryById(id, inventory), HttpStatus.OK);
    }

}
