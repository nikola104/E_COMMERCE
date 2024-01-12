package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.exception.InventoryNotFoundException;
import eCommerce.com.eCommerce.model.Inventory;
import eCommerce.com.eCommerce.repository.InventoryRepository;
import eCommerce.com.eCommerce.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void saveInventory(Inventory inventory) {
        inventoryRepository.save(inventory);

    }

    @Override
    public Long getInventoryById(Long id) {
        var inventory = inventoryRepository.findByProductId(id).orElseThrow(() -> new InventoryNotFoundException("Inventory not found!"));
        return inventory.getQuantity();
    }

    @Override
    public String updateInventoryById(Long id, Inventory inventory) {
        if(inventory.getQuantity() == null){
            throw new IllegalStateException("Quantity cannot be null!");
        }
        var inventoryToUpdate = inventoryRepository.findByProductId(id).orElseThrow(() -> new InventoryNotFoundException("Inventory not found!"));
        inventoryToUpdate.setQuantity(inventory.getQuantity());
        inventoryRepository.save(inventoryToUpdate);
        return "Inventory updated successfully!";
    }

}
