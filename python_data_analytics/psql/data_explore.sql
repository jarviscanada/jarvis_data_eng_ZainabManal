-- Show table schema 
\d+ retail;

-- Show first 10 rows
SELECT * FROM retail limit 10;

-- Check # of records
SELECT COUNT(*) FROM retail;

-- Number of unique clients
SELECT COUNT(DISTINCT customer_id) AS num_clients FROM retail;

-- Invoice date range 
SELECT MAX(invoice_date), MIN(invoice_date) FROM retail;

-- Number of SKU/merchants
SELECT COUNT(DISTINCT stock_code) AS num_stocks FROM retail;

-- Average invoice amount (excluding invoices with negative amounts/cancelled invoices)
SELECT AVG(invoice_amount) AS avg_invoice FROM (SELECT invoice_no, SUM(unit_price * quantity) AS invoice_amount FROM retail GROUP BY invoice_no HAVING SUM (unit_price * quantity) >= 0) AS valid_invoice ;

-- Total revenue
SELECT SUM(unit_price * quantity) AS total_revenue FROM retail;

-- Total revenue by YYYYMM
SELECT TO_CHAR(invoice_date, 'YYYYMM') AS date, SUM(unit_price * quantity) FROM retail GROUP BY date LIMIT 25;
