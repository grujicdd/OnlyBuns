-- Insert default users for testing
INSERT INTO users (username, first_name, last_name, email, password, role, deleted, created_at)
VALUES
    ('default_user', 'Djordje', 'Djokic', 'default@example.com', 'password', 'ROLE_USER', false, now()),
    ('admin_user', 'Pera', 'Peric', 'admin@example.com', 'adminpassword', 'ROLE_ADMIN', false, now()),
    ('test_user', 'Mika', 'Mikic', 'test@example.com', 'testpassword', 'ROLE_USER', false, now());

INSERT INTO public.posts(
    id, created_at, deleted, description, image_path, is_compressed, latitude, likes, longitude, title, author_id)
VALUES (1, '2024-11-08 13:44:17', false, 'post test 1', 'donpoll.jpg', false, NULL, 0, NULL, 'test title', 3);

INSERT INTO public.comments(
    id, author_username, content, created_at, deleted, post_id)
VALUES (1, 'test_user', 'test komentar 1', '2024-11-08 13:44:16', false, 1),
       (2, 'test_user', 'test komentar 2', '2024-11-08 13:44:17', false, 1);


