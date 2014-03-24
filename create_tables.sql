-- Dropping Database
DROP SCHEMA cs421g22 cascade;


-- Table Creation
CREATE SCHEMA cs421g22;
CREATE TABLE cs421g22.artists (
name
text NOT NULL,
website
text ,
CONSTRAINT pk_artists PRIMARY KEY ( name )
);
CREATE TABLE cs421g22.collections (
collection_id
SERIAL PRIMARY KEY,
name
text NOT NULL,
genre
text ,
creation_date
date DEFAULT current_date NOT NULL
);
COMMENT ON TABLE cs421g22.collections IS 'A generic collection of songs.';
CREATE TABLE cs421g22.links (
url
text NOT NULL,
source
varchar(100) NOT NULL,
CONSTRAINT pk_links PRIMARY KEY ( url )
);
CREATE TABLE cs421g22.playlists (
collection_id
integer NOT NULL,
likes_count
integer DEFAULT 0 NOT NULL,
CONSTRAINT pk_playlist PRIMARY KEY ( collection_id ),
CONSTRAINT fk_playlist FOREIGN KEY ( collection_id ) REFERENCES
cs421g22.collections( collection_id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE cs421g22.songs (
song_id
integer NOT NULL,
name
text ,
length
integer ,
CONSTRAINT pk_song PRIMARY KEY ( song_id )
);



CREATE TABLE cs421g22.users (
username
varchar(100) NOT NULL,
password
text NOT NULL,
super_user
bool DEFAULT 'false' NOT NULL,
CONSTRAINT pk_users PRIMARY KEY ( username )
);
CREATE TABLE cs421g22.users_subscriptions (
since
timestamp DEFAULT current_timestamp ,
subscriber
varchar NOT NULL,
subscribee
varchar NOT NULL,
CONSTRAINT idx_users_subscriptions_0 UNIQUE ( subscriber, subscribee ) ,
CONSTRAINT fk_users_subscriptions FOREIGN KEY ( subscriber ) REFERENCES
cs421g22.users( username ) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_users_subscriptions_0 FOREIGN KEY ( subscribee ) REFERENCES
cs421g22.users( username )
);
CREATE TABLE cs421g22.albums (
collection_id
integer NOT NULL,
year
smallint ,
song_count
smallint NOT NULL,
metadata
text ,
CONSTRAINT pk_albums PRIMARY KEY ( collection_id ),
CONSTRAINT fk_albums FOREIGN KEY ( collection_id ) REFERENCES
cs421g22.collections( collection_id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE cs421g22.link_to_song (
song_id
integer NOT NULL,
url
text NOT NULL,
CONSTRAINT idx_link_to_song_1 UNIQUE ( song_id, url ) ,
CONSTRAINT fk_link_to_song FOREIGN KEY ( song_id ) REFERENCES
cs421g22.songs( song_id ) ON UPDATE CASCADE,
CONSTRAINT fk_link_to_song_0 FOREIGN KEY ( url ) REFERENCES cs421g22.links(
url ) ON UPDATE CASCADE
);
CREATE TABLE cs421g22.song_by_artist (
song_id
integer NOT NULL,
artist_name
text NOT NULL,
CONSTRAINT idx_song_by_artist UNIQUE ( song_id, artist_name ) ,
CONSTRAINT fk_album_by_artist_0 FOREIGN KEY ( artist_name ) REFERENCES
cs421g22.artists( name )
,
CONSTRAINT fk_album_by_artist FOREIGN KEY ( song_id ) REFERENCES
cs421g22.songs( song_id )
);
CREATE TABLE cs421g22.song_in_album (



song_id
integer NOT NULL,
album_id
integer NOT NULL,
CONSTRAINT idx_song_in_album_1 UNIQUE ( song_id, album_id ) ,
CONSTRAINT fk_song_in_album FOREIGN KEY ( song_id ) REFERENCES
cs421g22.songs( song_id )
,
CONSTRAINT fk_song_in_album_0 FOREIGN KEY ( album_id ) REFERENCES
cs421g22.albums( collection_id )
);
CREATE TABLE cs421g22.song_in_playlist (
song_id
integer NOT NULL,
playlist_id
integer NOT NULL,
CONSTRAINT idx_song_in_playlist UNIQUE ( song_id, playlist_id ) ,
CONSTRAINT fk_song_in_collection FOREIGN KEY ( playlist_id ) REFERENCES
cs421g22.playlists( collection_id )
,
CONSTRAINT fk_song_in_playlist FOREIGN KEY ( song_id ) REFERENCES
cs421g22.songs( song_id )
);
CREATE TABLE cs421g22.user_likes_playlist (
collection_id
integer NOT NULL,
username
varchar(100) NOT NULL,
CONSTRAINT idx_user_likes_playlist_1 UNIQUE ( collection_id, username ) ,
CONSTRAINT fk_user_likes_playlist FOREIGN KEY ( collection_id ) REFERENCES
cs421g22.playlists( collection_id ) ON UPDATE CASCADE,
CONSTRAINT fk_user_likes_playlist_0 FOREIGN KEY ( username ) REFERENCES
cs421g22.users( username ) ON UPDATE CASCADE
);
