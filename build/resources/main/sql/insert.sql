INSERT INTO employees VALUES
    (1, 'Шелдон Купер', '4A, 2311 North Los Robles Avenue, Pasadena, California', '1980-02-26',
     '1 степень магистра, 2 докторских в Калифорнийском технологическом институте', 'директор',
     2),
    (2, 'Леонард Хофстедтер', '4A, 2311 North Los Robles Avenue, Pasadena, California', '1980-05-17',
     'Докторская степень в Калифорнийском технологическом институте', 'заместитель директора', 2),
    (3, 'Раджеш Кутраппали', 'Raj Mahal, Pasadena, California', '1981-10-06',
     'Диплом по астрофизике в Кембриджском университете, ' ||
     'докторская степень в Калифорнийском технологическом институте', 'главный аналитик', 2),
    (4, 'Говард Воловиц', 'Wolowitz House, Pasadena, California', '1981-03-01',
     'Степень магистра в области инженерии Массачусетского технологического института',
     'аэрокосмический инженер', 2);


INSERT INTO position_history (employee_id, position, date_of_attainment)
VALUES
    (1, 'директор', '2022-01-01'),
    (2, 'заместитель директора', '2022-01-01'),
    (3, 'главный аналитик', '2022-01-01'),
    (4, 'аэрокосмический инженер', '2023-02-18'),
    (4, 'инженер', '2022-01-01');

INSERT INTO projects VALUES
    (1, 'Project A', '2022-01-01', '2022-11-30'),
    (2, 'Project B', '2022-11-01', '2023-01-31'),
    (3, 'Project C', '2023-01-01', NULL);

INSERT INTO employees_on_projects (project_id, employee_id, role)
VALUES
    (1, 1, 'руководитель'),
    (1, 2, 'аналитик'),
    (1, 3, 'секретарь'),
    (1, 4, 'эксперт'),
    (2, 1, 'секретарь'),
    (2, 2, 'руководитель'),
    (2, 3, 'эксперт'),
    (2, 4, 'аналитик'),
    (3, 1, 'эксперт'),
    (3, 2, 'секретарь'),
    (3, 3, 'аналитик'),
    (3, 4, 'руководитель');

INSERT INTO payment_history (employee_id, type, payment_amount, date, is_award)
VALUES
    (1,'директор', 50000.00, '2022-02-01', false),
    (1,'директор', 50000.00, '2022-03-01', false),
    (1,'директор', 50000.00, '2022-04-01', false),
    (1,'директор', 50000.00, '2022-05-01', false),
    (1,'директор', 50000.00, '2022-06-01', false),
    (1,'директор', 50000.00, '2022-07-01', false),
    (1,'директор', 50000.00, '2022-08-01', false),
    (1,'директор', 50000.00, '2022-09-01', false),
    (1,'директор', 50000.00, '2022-10-01', false),
    (1,'директор', 50000.00, '2022-11-01', false),
    (1,'директор', 50000.00, '2022-12-01', false),
    (1,'директор', 50000.00, '2023-01-01', false),
    (1,'директор', 50000.00, '2023-02-01', false),
    (2, 'заместитель директора', 35000.00, '2022-02-01', false),
    (2, 'заместитель директора', 35000.00, '2022-03-01', false),
    (2, 'заместитель директора', 35000.00, '2022-04-01', false),
    (2, 'заместитель директора', 35000.00, '2022-05-01', false),
    (2, 'заместитель директора', 35000.00, '2022-06-01', false),
    (2, 'заместитель директора', 35000.00, '2022-07-01', false),
    (2, 'заместитель директора', 35000.00, '2022-08-01', false),
    (2, 'заместитель директора', 35000.00, '2022-09-01', false),
    (2, 'заместитель директора', 35000.00, '2022-10-01', false),
    (2, 'заместитель директора', 35000.00, '2022-11-01', false),
    (2, 'заместитель директора', 35000.00, '2022-12-01', false),
    (2, 'заместитель директора', 35000.00, '2023-01-01', false),
    (2, 'заместитель директора', 35000.00, '2023-02-01', false),
    (3, 'главный аналитик', 30000.00, '2022-02-01', false),
    (3, 'главный аналитик', 30000.00, '2022-03-01', false),
    (3, 'главный аналитик', 30000.00, '2022-04-01', false),
    (3, 'главный аналитик', 30000.00, '2022-05-01', false),
    (3, 'главный аналитик', 30000.00, '2022-06-01', false),
    (3, 'главный аналитик', 30000.00, '2022-07-01', false),
    (3, 'главный аналитик', 30000.00, '2022-08-01', false),
    (3, 'главный аналитик', 30000.00, '2022-09-01', false),
    (3, 'главный аналитик', 30000.00, '2022-10-01', false),
    (3, 'главный аналитик', 30000.00, '2022-11-01', false),
    (3, 'главный аналитик', 30000.00, '2022-12-01', false),
    (3, 'главный аналитик', 30000.00, '2023-01-01', false),
    (3, 'главный аналитик', 30000.00, '2023-02-01', false),
    (4, 'инженер', 25000.00, '2022-02-01', false),
    (4, 'инженер', 25000.00, '2022-03-01', false),
    (4, 'инженер', 25000.00, '2022-04-01', false),
    (4, 'инженер', 25000.00, '2022-05-01', false),
    (4, 'инженер', 25000.00, '2022-06-01', false),
    (4, 'инженер', 25000.00, '2022-07-01', false),
    (4, 'инженер', 25000.00, '2022-08-01', false),
    (4, 'инженер', 25000.00, '2022-09-01', false),
    (4, 'инженер', 25000.00, '2022-10-01', false),
    (4, 'инженер', 25000.00, '2022-11-01', false),
    (4, 'инженер', 25000.00, '2022-12-01', false),
    (4, 'инженер', 25000.00, '2023-01-01', false),
    (4, 'инженер', 25000.00, '2023-02-01', false),
    (1, 'разработка проекта X, руководитель', 75000.00, '2022-02-01', false),
    (1, 'разработка проекта X, руководитель', 75000.00, '2022-03-01', false),
    (1, 'разработка проекта X, руководитель', 75000.00, '2022-04-01', false),
    (1, 'разработка проекта X, руководитель', 75000.00, '2022-05-01', false),
    (1, 'разработка проекта X, руководитель', 75000.00, '2022-06-01', false),
    (1, 'разработка проекта X, руководитель', 75000.00, '2022-07-01', false),
    (1, 'разработка проекта X, руководитель', 75000.00, '2022-08-01', false),
    (1, 'разработка проекта X, руководитель', 75000.00, '2022-09-01', false),
    (1, 'разработка проекта X, руководитель', 75000.00, '2022-10-01', false),
    (1, 'разработка проекта X, руководитель', 75000.00, '2022-11-01', false),
    (1, 'разработка проекта X, руководитель', 75000.00, '2022-11-30', false),
    (2, 'разработка проекта X, аналитик', 50000.00, '2022-02-01', false),
    (2, 'разработка проекта X, аналитик', 50000.00, '2022-03-01', false),
    (2, 'разработка проекта X, аналитик', 50000.00, '2022-04-01', false),
    (2, 'разработка проекта X, аналитик', 50000.00, '2022-05-01', false),
    (2, 'разработка проекта X, аналитик', 50000.00, '2022-06-01', false),
    (2, 'разработка проекта X, аналитик', 50000.00, '2022-07-01', false),
    (2, 'разработка проекта X, аналитик', 50000.00, '2022-08-01', false),
    (2, 'разработка проекта X, аналитик', 50000.00, '2022-09-01', false),
    (2, 'разработка проекта X, аналитик', 50000.00, '2022-10-01', false),
    (2, 'разработка проекта X, аналитик', 50000.00, '2022-11-01', false),
    (2, 'разработка проекта X, аналитик', 50000.00, '2022-11-30', false),
    (3, 'разработка проекта X, секретарь', 30000.00, '2022-02-01', false),
    (3, 'разработка проекта X, секретарь', 30000.00, '2022-03-01', false),
    (3, 'разработка проекта X, секретарь', 30000.00, '2022-04-01', false),
    (3, 'разработка проекта X, секретарь', 30000.00, '2022-05-01', false),
    (3, 'разработка проекта X, секретарь', 30000.00, '2022-06-01', false),
    (3, 'разработка проекта X, секретарь', 30000.00, '2022-07-01', false),
    (3, 'разработка проекта X, секретарь', 30000.00, '2022-08-01', false),
    (3, 'разработка проекта X, секретарь', 30000.00, '2022-09-01', false),
    (3, 'разработка проекта X, секретарь', 30000.00, '2022-10-01', false),
    (3, 'разработка проекта X, секретарь', 30000.00, '2022-11-01', false),
    (3, 'разработка проекта X, секретарь', 30000.00, '2022-11-30', false),
    (4, 'разработка проекта X, эксперт', 60000.00, '2022-02-01', false),
    (4, 'разработка проекта X, эксперт', 60000.00, '2022-03-01', false),
    (4, 'разработка проекта X, эксперт', 60000.00, '2022-04-01', false),
    (4, 'разработка проекта X, эксперт', 60000.00, '2022-05-01', false),
    (4, 'разработка проекта X, эксперт', 60000.00, '2022-06-01', false),
    (4, 'разработка проекта X, эксперт', 60000.00, '2022-07-01', false),
    (4, 'разработка проекта X, эксперт', 60000.00, '2022-08-01', false),
    (4, 'разработка проекта X, эксперт', 60000.00, '2022-09-01', false),
    (4, 'разработка проекта X, эксперт', 60000.00, '2022-10-01', false),
    (4, 'разработка проекта X, эксперт', 60000.00, '2022-11-01', false),
    (4, 'разработка проекта X, эксперт', 60000.00, '2022-11-30', false),
    (1, 'разработка проекта Y, секретарь', 25000.00, '2022-12-01', false),
    (1, 'разработка проекта Y, секретарь', 25000.00, '2023-01-01', false),
    (1, 'разработка проекта Y, секретарь', 25000.00, '2023-01-31', false),
    (2, 'разработка проекта Y, руководитель', 80000.00, '2022-12-01', false),
    (2, 'разработка проекта Y, руководитель', 80000.00, '2023-01-01', false),
    (2, 'разработка проекта Y, руководитель', 80000.00, '2023-01-31', false),
    (3, 'разработка проекта Y, эксперт', 65000.00, '2022-12-01', false),
    (3, 'разработка проекта Y, эксперт', 65000.00, '2023-01-01', false),
    (3, 'разработка проекта Y, эксперт', 65000.00, '2023-01-31', false),
    (4, 'разработка проекта Y, аналитик', 55000.00, '2022-12-01', false),
    (4, 'разработка проекта Y, аналитик', 55000.00, '2023-01-01', false),
    (4, 'разработка проекта Y, аналитик', 55000.00, '2023-01-31', false),
    (1, 'разработка проекта Z, эксперт', 65000.00, '2023-02-01', false),
    (2, 'разработка проекта Z, секретарь', 25000.00, '2023-02-01', false),
    (3, 'разработка проекта Z, аналитик', 55000.00, '2023-02-01', false),
    (4, 'разработка проекта Z, руководитель', 80000.00, '2023-02-01', false),
    (1, 'до 1 года', 2000.00, '2022-12-31', true),
    (1, 'новый год', 2000.00, '2022-12-31', true),
    (2, 'до 1 года', 2000.00, '2022-12-31', true),
    (2, 'новый год', 2000.00, '2022-12-31', true),
    (3, 'до 1 года', 2000.00, '2022-12-31', true),
    (3, 'новый год', 2000.00, '2022-12-31', true),
    (4, 'до 1 года', 2000.00, '2022-12-31', true),
    (4, 'новый год', 2000.00, '2022-12-31', true),
    (1, 'день рождения', 3000.00, '2022-02-26', true),
    (2, 'день рождения', 3000.00, '2022-05-17', true),
    (3, 'день рождения', 3000.00, '2022-10-06', true),
    (4, 'день рождения', 3000.00, '2022-03-01', true);

INSERT INTO disbursement_policies (type, payment_amount) VALUES
    ('директор', 50000.00),
    ('заместитель директора', 35000.00),
    ('главный аналитик', 30000.00),
    ('инженер', 25000.00),
    ('аэрокосмический инженер', 32000.00),
    ('разработка проекта Z, руководитель', 80000.00),
    ('разработка проекта Z, аналитик', 55000.00),
    ('разработка проекта Z, секретарь', 25000.00),
    ('разработка проекта Z, эксперт', 65000.00),
    ('до 1 года', 2000.00),
    ('от 1 до 3 лет', 5000.00),
    ('от 3 до 5 лет', 8000.00),
    ('от 5 до 10 лет', 12000.00),
    ('свыше 10 лет', 20000.00),
    ('новый год', 2000.00),
    ('день рождения', 3000.00),
    ('5-летие компании', 4000.00),
    ('10-летие компании', 8000.00);
    