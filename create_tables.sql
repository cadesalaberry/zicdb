CREATE SCHEMA cs421g22;

CREATE TABLE cs421g22.artists (
    artist_id   BIGINT UNSIGNED,
    name        text NOT NULL,
    website     text,
    CONSTRAINT  pk_artists PRIMARY KEY ( artist_id )
);

CREATE TABLE cs421g22.collections (
    collection_id   BIGINT UNSIGNED,
    name            text NOT NULL,
    genre           text ,
    creation_date   date DEFAULT current_date NOT NULL,
    CONSTRAINT  pk_collections PRIMARY KEY ( collection_id )
);

COMMENT ON TABLE cs421g22.collections IS 'A generic collection of songs.';

CREATE TABLE cs421g22.links (
    link_id     BIGINT UNSIGNED,
    url         text NOT NULL,
    source      varchar(100) NOT NULL,
    CONSTRAINT  pk_links PRIMARY KEY ( link_id )
);

CREATE TABLE cs421g22.songs (
    song_id     BIGINT UNSIGNED,
    name        text ,
    length      BIGINT UNSIGNED ,
    CONSTRAINT  pk_songs PRIMARY KEY ( song_id )
);


CREATE TABLE cs421g22.users (
    user_id     BIGINT UNSIGNED,
    username    varchar(100) NOT NULL,
    password    text NOT NULL,
    super_user  bool DEFAULT 'false' NOT NULL,
    CONSTRAINT  pk_users PRIMARY KEY ( user_id )
);

CREATE TABLE cs421g22.playlists (
    playlist_id BIGINT UNSIGNED,
    likes_count BIGINT UNSIGNED DEFAULT 0 NOT NULL,
    CONSTRAINT  pk_playlists PRIMARY KEY ( playlist_id ),
    CONSTRAINT  fk_playlists FOREIGN KEY ( playlist_id )
    REFERENCES  cs421g22.collections( collection_id )
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE cs421g22.albums (
    album_id    BIGINT UNSIGNED NOT NULL,
    year        smallint ,
    song_count  smallint NOT NULL,
    metadata    text ,
    CONSTRAINT  pk_albums PRIMARY KEY ( album_id ),
    CONSTRAINT  fk_albums FOREIGN KEY ( album_id )
    REFERENCES  cs421g22.collections( collection_id )
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE cs421g22.users_subscriptions (
    subscriber  BIGINT UNSIGNED NOT NULL,
    subscribee  BIGINT UNSIGNED NOT NULL,
    since       timestamp DEFAULT current_timestamp ,
    CONSTRAINT  idx_users_subscriptions_0 UNIQUE ( subscriber, subscribee ) ,
    CONSTRAINT  fk_users_subscriptions FOREIGN KEY ( subscriber )
    REFERENCES  cs421g22.users( user_id )
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_users_subscriptions_0 FOREIGN KEY ( subscribee )
    REFERENCES  cs421g22.users( user_id )
);

CREATE TABLE cs421g22.song_has_link (
    song_id     BIGINT UNSIGNED NOT NULL,
    link_id     BIGINT UNSIGNED NOT NULL,
    CONSTRAINT  idx_song_has_link_1 UNIQUE ( song_id, link_id ) ,
    CONSTRAINT  fk_song_has_link FOREIGN KEY ( song_id )
    REFERENCES  cs421g22.songs( song_id )
    ON UPDATE CASCADE,
    CONSTRAINT  fk_song_has_link_0 FOREIGN KEY ( link_id )
    REFERENCES  cs421g22.links( link_id )
    ON UPDATE CASCADE
);

CREATE TABLE cs421g22.song_by_artist (
    song_id     BIGINT UNSIGNED NOT NULL,
    artist_id   BIGINT UNSIGNED NOT NULL,,
    CONSTRAINT  idx_song_by_artist UNIQUE ( song_id, artist_id ) ,
    CONSTRAINT  fk_album_by_artist_0 FOREIGN KEY ( artist_id )
    REFERENCES  cs421g22.artists( artist_id ),
    CONSTRAINT  fk_album_by_artist FOREIGN KEY ( song_id )
    REFERENCES  cs421g22.songs( song_id )
);

CREATE TABLE cs421g22.song_in_album (
    song_id     BIGINT UNSIGNED NOT NULL,
    album_id    BIGINT UNSIGNED NOT NULL,
    CONSTRAINT  idx_song_in_album_1 UNIQUE ( song_id, album_id ) ,
    CONSTRAINT  fk_song_in_album FOREIGN KEY ( song_id )
    REFERENCES  cs421g22.songs( song_id ),
    CONSTRAINT  fk_song_in_album_0 FOREIGN KEY ( album_id )
    REFERENCES  cs421g22.albums( collection_id )
);

CREATE TABLE cs421g22.song_in_playlist (
    song_id     BIGINT UNSIGNED NOT NULL,
    playlist_id BIGINT UNSIGNED NOT NULL,
    CONSTRAINT  idx_song_in_playlist UNIQUE ( song_id, playlist_id ) ,
    CONSTRAINT  fk_song_in_collection FOREIGN KEY ( playlist_id )
    REFERENCES  cs421g22.playlists( collection_id ),
    CONSTRAINT  fk_song_in_playlist FOREIGN KEY ( song_id )
    REFERENCES  cs421g22.songs( song_id )
);

CREATE TABLE cs421g22.user_likes_playlist (
    user_id     BIGINT UNSIGNED NOT NULL,
    playlist_id BIGINT UNSIGNED NOT NULL,
    CONSTRAINT  idx_user_likes_playlist_1 UNIQUE ( playlist_id, user_id ) ,
    CONSTRAINT  fk_user_likes_playlist FOREIGN KEY ( playlist_id )
    REFERENCES  cs421g22.playlists( playlist_id )
    ON UPDATE CASCADE,
    CONSTRAINT  fk_user_likes_playlist_0 FOREIGN KEY ( user_id )
    REFERENCES  cs421g22.users( user_id )
    ON UPDATE CASCADE
);

CREATE TABLE cs421g22.user_has_playlist (
    user_id     BIGINT UNSIGNED NOT NULL,
    playlist_id BIGINT UNSIGNED NOT NULL,
    since       timestamp DEFAULT current_timestamp,
    CONSTRAINT  idx_user_likes_playlist_1 UNIQUE ( playlist_id, user_id ) ,
    CONSTRAINT  fk_user_likes_playlist FOREIGN KEY ( playlist_id )
    REFERENCES  cs421g22.playlists( playlist_id )
    ON UPDATE CASCADE,
    CONSTRAINT  fk_user_likes_playlist_0 FOREIGN KEY ( user_id )
    REFERENCES  cs421g22.users( user_id )
    ON UPDATE CASCADE
);