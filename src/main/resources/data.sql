CREATE TABLE IF NOT EXISTS "transactions" (
                                             id SERIAL PRIMARY KEY,
                                             transaction_date VARCHAR,
                                             amount INT,
                                             brand_model VARCHAR(255)
    );

/*INSERT INTO "transactions" (transaction_date, amount, brand_model) VALUES
                                                                      ('2023-08-24', 500, 'Brand A - Model X'),
                                                                      ('2023-08-25', 750, 'Brand B - Model Y'),
                                                                      ('2023-08-25', 1000, 'Brand C - Model Z'),
                                                                      ('2023-08-26', 300, 'Brand A - Model Y'),
                                                                      ('2023-08-27', 1200, 'Brand D - Model A'),
                                                                      ('2023-08-27', 800, 'Brand B - Model Z'),
                                                                      ('2023-08-28', 600, 'Brand C - Model X'),
                                                                      ('2023-08-28', 950, 'Brand A - Model Z'),
                                                                      ('2023-08-29', 700, 'Brand E - Model Y'),
                                                                      ('2023-08-29', 1100, 'Brand F - Model X'),
                                                                      ('2023-09-15', 300, 'Brand G - Model Y'),
                                                                      ('2023-09-16', 850, 'Brand B - Model X'),
                                                                      ('2023-09-16', 500, 'Brand C - Model Z'),
                                                                      ('2023-09-17', 750, 'Brand D - Model Y'),
                                                                      ('2023-09-17', 1200, 'Brand A - Model X');*/