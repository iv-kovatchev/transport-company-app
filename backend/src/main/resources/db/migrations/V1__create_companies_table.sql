CREATE TABLE companies
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(100) NOT NULL UNIQUE,
    registration_number VARCHAR(20) UNIQUE,
    address             VARCHAR(200),
    phone               VARCHAR(20),
    email               VARCHAR(100),
    created_at          DATETIME     NOT NULL,
    updated_at          DATETIME     NOT NULL,
    INDEX               idx_company_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;