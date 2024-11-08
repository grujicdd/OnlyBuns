insert into teacher (first_name, last_name, deleted) values ('Strahinja', 'Simić', false);
insert into teacher (first_name, last_name, deleted) values ('Marina', 'Antić', false);
insert into teacher (first_name, last_name, deleted) values ('Siniša', 'Branković', false);

-- Insert default users for testing
INSERT INTO users (username, email, password, role, deleted, created_at)
VALUES
    ('default_user', 'default@example.com', 'password', 'ROLE_USER', false, now()),
    ('admin_user', 'admin@example.com', 'adminpassword', 'ROLE_ADMIN', false, now()),
    ('test_user', 'test@example.com', 'testpassword', 'ROLE_USER', false, now());
