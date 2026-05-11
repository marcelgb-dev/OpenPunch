-- Codificación utf8 para usar acentos
SET NAMES 'utf8mb4';
SET CHARACTER SET utf8mb4;

-- =============================================
-- 2. INSERTAR USUARIOS (4 Usuarios)
-- =============================================
INSERT INTO `users` (`id`, `token`, `username`, `password`, `role`, `name`, `surname`) VALUES
(1, 'KqspOhPT8Z', 'user1', '$2a$10$GO89Y9eE5mqFqtpSYjavauqY8prPtBd.OWcP0j07Kg5PC7B.8IrrW', 3, 'NAME_1', 'SURNAME_1'),      -- Usuario de prueba 1
(2, 'QQ0Nyug3pj', 'user2', '$2a$10$GO89Y9eE5mqFqtpSYjavauqY8prPtBd.OWcP0j07Kg5PC7B.8IrrW', 3, 'NAME_2', 'SURNAME_2');       -- Usuario de prueba 2


-- =============================================
-- 3. INSERTAR LOGS (Historial de eventos)
-- Simulamos actividad de ayer y hoy
-- =============================================

-- NAME_1 (ID 2): Turno completo ayer
INSERT INTO `punch_logs` (`id`, `user_id`, `log_time`, `event`) VALUES 
(1, 1, '2026-04-19 08:05:00', 'IN'),
(2, 1, '2026-04-19 16:15:00', 'OUT');

-- NAME_2 (ID 3): Turno completo ayer
INSERT INTO `punch_logs` (`id`, `user_id`, `log_time`, `event`) VALUES 
(3, 2, '2026-04-19 09:00:00', 'IN');

-- =============================================
-- 4. INSERTAR SESIONES
-- =============================================

-- Sesión de NAME_1 (6h 20m = 380 min)
INSERT INTO `work_sessions` (`user_id`, `start_log_id`, `end_log_id`, `start_time`, `end_time`, `duration_minutes`)
VALUES (1, 1, 2, '2026-04-19 14:10:00', '2026-04-19 20:30:00', 490);

-- Sesión de NAME_2 (abierta)
INSERT INTO `work_sessions` (`user_id`, `start_log_id`, `start_time`)
VALUES (2, 3,  '2026-04-20 09:00:00');