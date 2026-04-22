-- Codificación utf8 para usar acentos
SET NAMES 'utf8mb4';
SET CHARACTER SET utf8mb4;

-- =============================================
-- 1. INSERTAR GRUPOS DE USUARIOS
-- =============================================
INSERT INTO `groups` (`id`, `group_name`) VALUES
(1, 'Desarrollo'),
(2, 'Administración');

-- =============================================
-- 2. INSERTAR USUARIOS (5 Usuarios)
-- Usamos UUID() para simular lo que haría Java
-- =============================================
INSERT INTO `users` (`id`, `group_id`, `qr_token`, `username`, `password`, `role`, `name`, `surname`) VALUES
(1, 1, UUID(), 'admin', 'admin_hash', 1, 'Andrés', 'García'),       -- Admin en Desarrollo
(2, 1, UUID(), 'marta.dev', 'pass123', 2, 'Marta', 'López'),      -- Usuario en Desarrollo
(3, 1, UUID(), 'lucas.dev', 'pass123', 2, 'Lucas', 'Ruiz'),       -- Usuario en Desarrollo
(4, 2, UUID(), 'elena.adm', 'pass123', 2, 'Elena', 'Sanz'),       -- Usuario en Admin
(5, 2, UUID(), 'pablo.adm', 'pass123', 2, 'Pablo', 'Gómez');      -- Usuario en Admin

-- =============================================
-- 3. INSERTAR LOGS (Historial de eventos)
-- Simulamos actividad de ayer y hoy
-- =============================================

-- MARTA (ID 2): Turno completo ayer
INSERT INTO `punch_logs` (`id`, `user_id`, `log_time`, `event`) VALUES 
(1, 2, '2026-04-19 08:05:00', 'IN'),
(2, 2, '2026-04-19 16:15:00', 'OUT');

-- LUCAS (ID 3): Turno completo ayer
INSERT INTO `punch_logs` (`id`, `user_id`, `log_time`, `event`) VALUES 
(3, 3, '2026-04-19 09:00:00', 'IN'),
(4, 3, '2026-04-19 18:00:00', 'OUT');

-- ELENA (ID 4): Turno completo hoy por la mañana
INSERT INTO `punch_logs` (`id`, `user_id`, `log_time`, `event`) VALUES 
(5, 4, '2026-04-20 07:30:00', 'IN'),
(6, 4, '2026-04-20 13:30:00', 'OUT');

-- PABLO (ID 5): Fichó hoy y sigue DENTRO (No tiene OUT)
INSERT INTO `punch_logs` (`id`, `user_id`, `log_time`, `event`) VALUES 
(7, 5, '2026-04-20 09:00:00', 'IN');

-- ANDRÉS (ID 1): Fichó hoy y sigue DENTRO
INSERT INTO `punch_logs` (`id`, `user_id`, `log_time`, `event`) VALUES 
(8, 1, '2026-04-20 10:00:00', 'IN');

-- =============================================
-- 4. INSERTAR SESIONES (Turnos cerrados)
-- Solo para los que tienen un evento OUT
-- =============================================

-- Sesión de Marta (8h 10m = 490 min)
INSERT INTO `work_sessions` (`user_id`, `start_log_id`, `end_log_id`, `start_time`, `end_time`, `duration_minutes`)
VALUES (2, 1, 2, '2026-04-19 08:05:00', '2026-04-19 16:15:00', 490);

-- Sesión de Lucas (9h = 540 min)
INSERT INTO `work_sessions` (`user_id`, `start_log_id`, `end_log_id`, `start_time`, `end_time`, `duration_minutes`)
VALUES (3, 3, 4, '2026-04-19 09:00:00', '2026-04-19 18:00:00', 540);

-- Sesión de Elena (6h = 360 min)
INSERT INTO `work_sessions` (`user_id`, `start_log_id`, `end_log_id`, `start_time`, `end_time`, `duration_minutes`)
VALUES (4, 5, 6, '2026-04-20 07:30:00', '2026-04-20 13:30:00', 360);

-- Sesión de Andrés (abierta)
INSERT INTO `work_sessions` (`user_id`, `start_log_id`, `start_time`)
VALUES (1, 8,  '2026-04-20 10:00:00');

-- Sesión de Pablo (abierta)
INSERT INTO `work_sessions` (`user_id`, `start_log_id`, `start_time`)
VALUES (5, 7,  '2026-04-20 09:00:00');