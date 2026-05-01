-- Codificación utf8 para usar acentos
SET NAMES 'utf8mb4';
SET CHARACTER SET utf8mb4;

-- =============================================
-- 2. INSERTAR USUARIOS (4 Usuarios)
-- =============================================
INSERT INTO `users` (`id`, `token`, `username`, `password`, `role`, `name`, `surname`) VALUES
(1, UUID(), 'admin', 'pass123', 1, 'ADMIN', ''),       -- Admin
(2, UUID(), 'user1', 'pass123', 2, 'NAME_1', 'SURNAME_1'),      -- Usuario de prueba 1
(3, UUID(), 'user2', 'pass123', 2, 'NAME_2', 'SURNAME_2'),       -- Usuario de prueba 2
(4, UUID(), 'scanner', 'pass123', 3, 'SCANNER', '');       -- Scanner

-- =============================================
-- 3. INSERTAR LOGS (Historial de eventos)
-- Simulamos actividad de ayer y hoy
-- =============================================

-- NAME_1 (ID 2): Turno completo ayer
INSERT INTO `punch_logs` (`id`, `user_id`, `log_time`, `event`) VALUES 
(1, 2, '2026-04-19 08:05:00', 'IN'),
(2, 2, '2026-04-19 16:15:00', 'OUT');

-- NAME_2 (ID 3): Turno completo ayer
INSERT INTO `punch_logs` (`id`, `user_id`, `log_time`, `event`) VALUES 
(3, 3, '2026-04-19 09:00:00', 'IN');

-- =============================================
-- 4. INSERTAR SESIONES
-- =============================================

-- Sesión de NAME_1 (8h 10m = 490 min)
INSERT INTO `work_sessions` (`user_id`, `start_log_id`, `end_log_id`, `start_time`, `end_time`, `duration_minutes`)
VALUES (2, 1, 2, '2026-04-19 08:05:00', '2026-04-19 16:15:00', 490);

-- Sesión de NAME_2 (abierta)
INSERT INTO `work_sessions` (`user_id`, `start_log_id`, `start_time`)
VALUES (3, 3,  '2026-04-20 09:00:00');