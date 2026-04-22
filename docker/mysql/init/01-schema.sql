-- 1. Grupos
CREATE TABLE `groups` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Usuarios
CREATE TABLE `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `group_id` INT NOT NULL,
  `qr_token` VARCHAR(36) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` TINYINT NOT NULL DEFAULT '2',
  `name` VARCHAR(50) NOT NULL,
  `surname` VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `qr_token_UNIQUE` (`qr_token`),
  CONSTRAINT `fk_user_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Logs de fichaje
CREATE TABLE `punch_logs` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `log_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `event` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_punch_logs_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Sesiones de trabajo
CREATE TABLE `work_sessions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `start_log_id` INT NOT NULL,
  `end_log_id` INT DEFAULT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME DEFAULT NULL,
  `duration_minutes` INT DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_session_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_session_start` FOREIGN KEY (`start_log_id`) REFERENCES `punch_logs` (`id`),
  CONSTRAINT `fk_session_end` FOREIGN KEY (`end_log_id`) REFERENCES `punch_logs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--  VIEWS
-- user_status: Users (minus login credentials) + last Log
CREATE VIEW view_user_status AS
SELECT
    u.id AS user_id,
    u.group_id,
    u.qr_token,
    u.name,
    u.surname,
    u.role,
    l.id AS last_log_id,
    l.log_time as last_log_time,
    l.event as last_log_event
FROM users u LEFT JOIN punch_logs l ON l.id = (
    SELECT MAX(id) FROM punch_logs WHERE user_id = u.id
);

-- Logs + basic data from their Users
CREATE VIEW view_punch_logs AS
SELECT
    l.id AS log_id,
    l.user_id,
    l.log_time,
    l.event,
    u.group_id,
    u.name,
    u.surname,
    u.role
FROM punch_logs l JOIN users u ON l.user_id = u.id;

-- WorkSessions + basic data from their Users
CREATE VIEW view_work_sessions AS
SELECT
    w.id AS work_session_id,
    w.user_id,
    w.start_time,
    w.end_time,
    w.duration_minutes,
    u.group_id,
    u.name,
    u.surname,
    u.role
FROM work_sessions w JOIN users u ON w.user_id = u.id;