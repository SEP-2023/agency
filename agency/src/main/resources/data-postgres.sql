INSERT INTO public.roles(id, name) VALUES ('1','ROLE_GOVERNMENT');
INSERT INTO public.clients(
    id, deleted, email, enabled, name, password, surname)
VALUES (1, false, 'pera@gmail.com', true, 'Pera', '$2a$10$KiXpjijxmt2K1qIL1kN6nOxj/HV3iDoSzrsGGdfi9KU4eZ18gZJqO', 'Peric');
INSERT INTO public.user_role(
    user_id, role_id)
VALUES (1, 1);