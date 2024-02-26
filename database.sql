CREATE DATABASE db_simpeg_development;

USE db_simpeg_development;

CREATE TABLE users
(
    username    VARCHAR(100) NOT NULL,
    password    VARCHAR(100) NOT NULL,
    name        VARCHAR(100) NOT NULL,
    token       VARCHAR(100),
    token_expired_at    BIGINT,
    PRIMARY KEY (username),
    UNIQUE (token)
);

CREATE TABLE departments
(
    code VARCHAR(4) NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (code)
);

INSERT INTO departments VALUES
("HRD", "Human Resources Department"),
("FAT", "Finance Accounting & Tax"),
("GA", "General Affair"),
("ENG", "Engineering"),
("PRO", "Production"),
("SVY", "Surveyor"),
("LOG", "Logistic"),
("PLA", "Plant and Maintenance"),
("SEC", "Security"),
("HSE", "Health Safety & Environment");

CREATE TABLE employee_positions
(
    id         INT          NOT NULL AUTO_INCREMENT,
    name       VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO employee_positions VALUES
("1", "Manager"),
("2", "Supervisor"),
("3", "Operator"),
("4", "Staff");

CREATE table employees
(
    id_number               CHAR(16) NOT NULL,
    name                    VARCHAR(100) NOT NULL,
--    gender: {
--        M: Male
--        F: Female
--    }
    gender                  ENUM('M', 'F') NOT NULL,
    department_code         VARCHAR(4) NOT NULL,
    entry_date              DATE NOT NULL,
    address                 VARCHAR(255) NOT NULL,
    city                    VARCHAR(100) NOT NULL,
    origin_city             VARCHAR(100) NOT NULL,
    birthplace              VARCHAR(100) NOT NULL,
    birthdate               DATE NOT NULL,
    employee_position_id    INT NOT NULL,
    --    religion: {
    --        I: Islam
    --        CP: Christian Protestant
    --        CC: Christian Catholic
    --        H: Hindu
    --        B: Buddhist
    --        K: Confucian / Konghucu
    --    }
    religion                ENUM('I', 'CP', 'CC', 'H', 'B', 'K') NOT NULL,
    --    citizen: {
    --        I: Indonesia
    --        F: Foreign
    --    }
    citizen                 ENUM('I', 'F') NOT NULL,
    --    marital_status: {
    --        S: Single
    --        M: Married
    --    }
    marital_status          ENUM('S', 'M') NOT NULL,
    income_tax_status       ENUM('TK', 'K/0', 'K/1', 'K/2', 'K/3') NOT NULL,
    blood_type              ENUM('A', 'AB', 'B', 'O') NOT NULL,
    bpjs_health             CHAR(16),
    bpjs_employment         CHAR(16),
    bpjs_retirement         CHAR(16),
    profile_picture         VARCHAR(100),
    PRIMARY KEY (id_number),
    FOREIGN KEY (employee_position_id) REFERENCES employee_positions (id)
);

CREATE TABLE contracts
(
    id                      INT NOT NULL AUTO_INCREMENT,
    employee_id             CHAR(16) NOT NULL,
    --    contract_status: {
    --        C: Contract
    --        P: Permanent
    --    }
    contract_status         ENUM('C', 'P') NOT NULL,
    start_date              DATE NOT NULL,
    contract_length_month   TINYINT(3) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (employee_id) REFERENCES employees (id_number)
);

CREATE TABLE spouses
(
    id_number               CHAR(16) NOT NULL,
    name                    VARCHAR(100) NOT NULL,
    birthplace              VARCHAR(100) NOT NULL,
    birthdate               DATE NOT NULL,
    date_of_marriage        DATE NOT NULL,
    spouse_sequence         TINYINT(2) NOT NULL,
    last_education          CHAR(3) NOT NULL,
    occupation              VARCHAR(30) NOT NULL,
    employee_id             CHAR(16) NOT NULL,
    PRIMARY KEY (id_number),
    FOREIGN KEY (employee_id) REFERENCES employees (id_number)
);

CREATE TABLE children
(
    id_number               CHAR(16) NOT NULL,
    name                    VARCHAR(100) NOT NULL,
    birthplace              VARCHAR(100) NOT NULL,
    birthdate               DATE NOT NULL,
--    gender: {
--        M: Male
--        F: Female
--    }
    gender                  ENUM('M', 'F') NOT NULL,
    child_sequence          TINYINT(2) NOT NULL,
    last_education          CHAR(3) NOT NULL,
    occupation              VARCHAR(30) NOT NULL,
--    child_status: {
--        BC: Biological Child
--        SC: Step Child
--        AC: Adopted Child
--    }
    child_status            ENUM('BC', 'SC', 'AC') NOT NULL,
    employee_id             CHAR(16) NOT NULL,
    PRIMARY KEY (id_number),
    FOREIGN KEY (employee_id) REFERENCES employees (id_number)
);

CREATE TABLE parents
(
    id_number                   CHAR(16) NOT NULL,
    name                        VARCHAR(100) NOT NULL,
    birthplace                  VARCHAR(100) NOT NULL,
    birthdate                   DATE NOT NULL,
--    gender: {
--        M: Male
--        F: Female
--    }
    gender                      ENUM('M', 'F') NOT NULL,
    last_education              CHAR(3) NOT NULL,
    occupation                  VARCHAR(30) NOT NULL,
--    parent_status: {
--        BF: Biological Father
--        BM: Biological Mother
--    }
    parent_status               ENUM('BF', 'BM') NOT NULL,
    employee_id                 CHAR(16) NOT NULL,
    PRIMARY KEY (id_number),
    FOREIGN KEY (employee_id)   REFERENCES employees (id_number)
);

CREATE TABLE educations
(
    id                          INT NOT NULL AUTO_INCREMENT,
    education_level             CHAR(3) NOT NULL,
    major                       VARCHAR(30) NOT NULL,
    name                        VARCHAR(100) NOT NULL,
    location                    VARCHAR(100) NOT NULL,
    graduation_year             TINYINT(4) NOT NULL,
    certificate_number          VARCHAR(25) NOT NULL,
    employee_id                 CHAR(16) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (employee_id)   REFERENCES employees (id_number)
);

CREATE TABLE work_experiences
(
    id                          INT NOT NULL AUTO_INCREMENT,
    company_name                VARCHAR(100) NOT NULL,
    type                        VARCHAR(30) NOT NULL,
    location                    VARCHAR(100) NOT NULL,
    department                  VARCHAR(50) NOT NULL,
    employee_position_id        INT NOT NULL,
    initial_period              DATE NOT NULL,
    final_period                DATE NOT NULL,
    employee_id                 CHAR(16) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (employee_id)   REFERENCES employees (id_number),
    FOREIGN KEY (employee_position_id) REFERENCES employee_positions (id)
);

CREATE TABLE warning_letters
(
    id                                  INT NOT NULL AUTO_INCREMENT,
    date_facing_hrd                     DATE NOT NULL,
--    regarding: {
--        R: Reprimand
--        W1: Warning 1
--        W2: Warning 2
--        W3: Warning 3
--        S: Suspension
--    }
    regarding                           ENUM('R', 'W1', 'W2', 'W3', 'S') NOT NULL,
    violation_1                         VARCHAR(100),
    violation_2                         VARCHAR(100),
    suspension_period                   DATE,
    employee_id                         CHAR(16) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (employee_id)           REFERENCES employees (id_number)
);