INSERT INTO ecom_products (code, name, description, image, category, price, quantity, internal_reference, shell_id, inventory_status, rating, created_at, updated_at)
VALUES
  ('P001', 'Apple iPhone 13', 'Latest Apple iPhone featuring A15 Bionic chip and advanced dual-camera system', 'https://example.com/images/iphone13.jpg', 'Smartphones', 799.99, 25, 'APPLE-IP13', 100, 'INSTOCK', 4.8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('P002', 'Samsung Galaxy S22', 'High-end smartphone with a dynamic AMOLED display and pro-grade camera', 'https://example.com/images/galaxy-s22.jpg', 'Smartphones', 699.99, 30, 'SAMSUNG-S22', 101, 'INSTOCK', 4.6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('P003', 'Sony WH-1000XM4', 'Industry-leading noise-cancelling headphones with premium sound quality', 'https://example.com/images/sony-wh1000xm4.jpg', 'Audio', 349.99, 15, 'SONY-WH1000XM4', 102, 'INSTOCK', 4.7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('P004', 'LG 4K OLED TV', 'Premium OLED television with stunning 4K visuals and smart features', 'https://example.com/images/lg-oledtv.jpg', 'Televisions', 1199.99, 10, 'LG-OLEDTV', 103, 'LOWSTOCK', 4.9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('P005', 'Dell XPS 15', 'Powerful laptop featuring Intel Core i7, 16GB RAM, and a vibrant 15-inch display', 'https://example.com/images/dell-xps15.jpg', 'Computers', 1299.99, 8, 'DELL-XPS15', 104, 'INSTOCK', 4.5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (code) DO NOTHING;
