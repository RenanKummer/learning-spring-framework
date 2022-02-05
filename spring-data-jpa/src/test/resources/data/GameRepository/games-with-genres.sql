-- GAMES
insert into games (id, title) values (1, 'Super Mario Odyssey');
insert into games (id, title) values (2, 'Legend of Zelda: Breath of the Wild');

-- GENRES
insert into genres (id, genre) values (1, 'Action');
insert into genres (id, genre) values (2, '3D Platform');
insert into genres (id, genre) values (3, 'Open-World');

--GAMES_GENRES
insert into games_genres (game_id, genre_id) values (1, 2);
insert into games_genres (game_id, genre_id) values (1, 3);
insert into games_genres (game_id, genre_id) values (2, 1);
insert into games_genres (game_id, genre_id) values (2, 3);
