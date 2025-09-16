INSERT INTO categories (name)
VALUES ('Produce'),
       ('Dairy'),
       ('Bakery'),
       ('Meat & Seafood'),
       ('Pantry Staples'),
       ('Beverages');
INSERT INTO products (name, price, description, category_id)
VALUES ('Bananas', 59, 'Fresh organic bananas sold per pound.', 1),
       ('Whole Milk', 349, '1 gallon of whole milk from grass-fed cows.', 2),
       ('Sourdough Bread', 425, 'Artisan sourdough bread with a crispy crust.', 3),
       ('Chicken Breast', 699, 'Boneless, skinless chicken breasts per pound.', 4),
       ('Brown Rice', 279, '2 lb bag of long-grain brown rice.', 5),
       ('Orange Juice', 450, 'Fresh squeezed orange juice, no added sugar.', 6),
       ('Apples', 199, 'Fuji apples sold per pound, sweet and crisp.', 1),
       ('Cheddar Cheese', 525, 'Sharp cheddar cheese block, 8 oz.', 2),
       ('Ground Beef', 599, '80/20 ground beef, great for burgers.', 4),
       ('Olive Oil', 899, 'Extra virgin olive oil, 500ml bottle.', 5);