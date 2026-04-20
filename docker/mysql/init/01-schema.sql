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

-- 3. Logs de fichaje (La "Caja Negra")
CREATE TABLE `punch_logs` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `log_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `event` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_punch_logs_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Sesiones de trabajo (La tabla para cálculos rápidos)
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
