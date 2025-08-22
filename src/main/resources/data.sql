INSERT INTO category (id, name, color)
SELECT 1, '未分類', '#666666'
WHERE NOT EXISTS (
  SELECT 1 FROM category WHERE name = '未分類'
);