CREATE VIEW all_artists AS
SELECT username FROM users;

CREATE VIEW playlists_from_user AS
SELECT C.collection_id, C.name
FROM collections C, user_has_playlist P, users U
WHERE C.collection_id = P.collection_id
AND   P.user_id = U.user_id
AND   U.username = ?;

CREATE VIEW songs_from_playlist AS
SELECT S.song_id, S.name
FROM songs S, song_in_playlist SP, playlist P, collections C
WHERE S.song_id = SP.song_id
AND   SP.playlist_id = P.playlist_id
AND   P.playlist_id = C.collection_id
AND   C.name = ?;

CREATE VIEW albums_from_artist AS
SELECT C.collection_id, C.name
FROM collections C, user_has_playlist P, users U
WHERE C.collection_id = P.collection_id
AND   P.user_id = U.user_id
AND   U.username = ?;

CREATE VIEW songs_from_album AS
SELECT S.song_id S.name
FROM songs S, song_in_album SA, albums A, collections C
WHERE S.song_id = SA.song_id
AND   SA.album_id = A.album_id
AND   A.album_id = C.collection_id
AND   C.name = ?;