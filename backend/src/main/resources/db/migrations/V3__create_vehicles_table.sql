CREATE TABLE vehicles
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    license_plate       VARCHAR(20) NOT NULL UNIQUE,
    type                VARCHAR(20) NOT NULL COMMENT 'BUS, TRUCK, TANKER, VAN, CAR',
    brand               VARCHAR(100),
    model               VARCHAR(100),
    year                INT,
    capacity_kg         DECIMAL(10, 2),
    capacity_passengers INT,
    company_id          BIGINT      NOT NULL,
    created_at          DATETIME    NOT NULL,
    updated_at          DATETIME    NOT NULL,
    CONSTRAINT fk_vehicle_company FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE,
    CONSTRAINT uk_vehicle_license_plate UNIQUE (license_plate),
    INDEX               idx_company_id (company_id),
    INDEX               idx_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;