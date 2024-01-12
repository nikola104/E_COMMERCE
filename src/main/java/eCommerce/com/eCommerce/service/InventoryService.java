package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.model.Inventory;

import java.util.List;

public interface InventoryService {

    void saveInventory(Inventory inventory);

    Long getInventoryById(Long id);

    String updateInventoryById(Long id, Inventory inventory);


}
