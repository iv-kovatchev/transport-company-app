CREATE TABLE employees
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100)   NOT NULL,
    last_name  VARCHAR(100)   NOT NULL,
    phone      VARCHAR(20),
    email      VARCHAR(100),
    salary     DECIMAL(10, 2),
    company_id BIGINT         NOT NULL,
    created_at DATETIME       NOT NULL,
    updated_at DATETIME       NOT NULL,
    CONSTRAINT fk_employee_company FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE,
    INDEX      idx_employee_company_id (company_id),
    INDEX      idx_employee_salary (salary)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;