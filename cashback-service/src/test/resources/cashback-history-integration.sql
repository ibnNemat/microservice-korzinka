delete from cashback_history;
insert into cashback_history(id, after_transaction, before_transaction, cashback_card_id,
                             transaction_amount, transaction_date)
        VALUES (1, 10000, 2000, 1, 8000, '2022-01-10'),
               (2, 5000, 15000, 1, 10000, '2022-03-25'),
               (3, 5000, 15000, 1, 10000, '2022-04-25'),
               (4, 30000, 23000, 1, 7000, '2022-07-07'),
               (5, 12000, 10000, 1, 2000, '2023-03-01'),
               (6, 70000, 55000, 1, 15000, '2023-05-20');

