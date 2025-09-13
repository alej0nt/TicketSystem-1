-- Crear base de datos
CREATE DATABASE IF NOT EXISTS ticket_system;

-- Usar base de datos
USE ticket_system;

-- =======================================
-- TABLA EMPLOYEE
-- =======================================
CREATE TABLE employee ( 
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'AGENTE', 'USER') NOT NULL,
    department VARCHAR(100)
);

-- =======================================
-- TABLA CATEGORY
-- =======================================
CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

-- =======================================
-- TABLA TICKET
-- =======================================
CREATE TABLE ticket (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    category_id BIGINT NOT NULL,
    priority ENUM('Baja', 'Media', 'Alta', 'Critica') NOT NULL DEFAULT 'Media',
    state ENUM('Abierto', 'En progreso', 'Resuelto', 'Cerrado') NOT NULL DEFAULT 'Abierto',
    creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    closing_date DATETIME NULL,
    CONSTRAINT fk_ticket_employee FOREIGN KEY (employee_id) REFERENCES employee(id),
    CONSTRAINT fk_ticket_category FOREIGN KEY (category_id) REFERENCES category(id)
);

-- =======================================
-- TABLA ASSIGNMENT
-- =======================================
CREATE TABLE assignment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    assignment_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_assignment_ticket FOREIGN KEY (ticket_id) REFERENCES ticket(id),
    CONSTRAINT fk_assignment_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);

-- =======================================
-- TABLA COMMENT
-- =======================================
CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    text TEXT NOT NULL,
    creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comment_ticket FOREIGN KEY (ticket_id) REFERENCES ticket(id),
    CONSTRAINT fk_comment_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);

-- =======================================
-- TABLA TICKET RECORD (HISTORIAL)
-- =======================================
CREATE TABLE ticket_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_id BIGINT NOT NULL,
    previous_state ENUM('Abierto', 'En progreso', 'Resuelto', 'Cerrado') NOT NULL,
    new_state ENUM('Abierto', 'En progreso', 'Resuelto', 'Cerrado') NOT NULL,
    changed_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ticketRecord_ticket FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);

-- =======================================
-- TABLA EVIDENCE
-- =======================================
CREATE TABLE evidence (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    url VARCHAR(500) NOT NULL,
    ticket_id BIGINT NOT NULL,
    CONSTRAINT fk_evidence_ticket FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);

-- =======================================
-- TABLA NOTIFICATION
-- =======================================
CREATE TABLE notification (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notification_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);
