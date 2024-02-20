-- Remove the 'image_data' column from the 'product' table
ALTER TABLE product
DROP COLUMN image_data;

-- Add a new column 'image_path' to the 'product' table
ALTER TABLE product
ADD COLUMN image_path VARCHAR(255);