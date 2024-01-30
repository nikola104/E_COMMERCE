ALTER TABLE product
ADD CONSTRAINT fk_inventory
FOREIGN KEY (inventory_id) REFERENCES inventory(id);