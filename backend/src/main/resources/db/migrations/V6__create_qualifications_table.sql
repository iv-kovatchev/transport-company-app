CREATE TABLE qualifications
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id        BIGINT      NOT NULL,
    qualification_type VARCHAR(50) NOT NULL COMMENT 'HAZARDOUS_MATERIALS, PASSENGER_TRANSPORT_12_PLUS, LONG_DISTANCE, INTERNATIONAL, HEAVY_CARGO, TANKER_TRANSPORT',
    created_at         DATETIME    NOT NULL,
    CONSTRAINT fk_qualification_employee FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE CASCADE,
    CONSTRAINT uk_employee_qualification UNIQUE (employee_id, qualification_type),
    INDEX              idx_qualification_employee_id (employee_id),
    INDEX              idx_qualification_type (qualification_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;