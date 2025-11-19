--------------------------------------------
--  SLOTS
--------------------------------------------
INSERT INTO slots (id, center_id, date, start_time, end_time, capacity, seats_remaining, status, created_at, updated_at)
VALUES
(1, 101, '2025-11-19', '06:00:00', '07:00:00', 20, 10, 'OPEN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 101, '2025-11-19', '07:00:00', '08:00:00', 20, 1, 'OPEN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 101, '2025-11-19', '18:00:00', '19:00:00', 20, 0, 'FULL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--------------------------------------------
--  BOOKINGS
--------------------------------------------
INSERT INTO bookings (id, user_id, slot_id, center_id, status, booked_at, updated_at)
VALUES
(1001, 10, 1, 101, 'CONFIRMED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1002, 11, 3, 101, 'CONFIRMED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

