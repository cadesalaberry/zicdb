# zicdb
________

A database project aiming to gather links to songs from the web using postgreSQL.

## Create the database

	psql -d CS421 -u cs421g22 -a -f create_tables.sql

## Populating the database

	psql -d CS421 -u cs421g22 -a -f populate_tables.sql

## Relational Schema

You can access the visual schema on http://bit.ly/ecse421-project-schema.

	Entities:

	artists(name, website)
	links(url, source)
	albums(album_id, year, song_count, metadata)
	songs(song_id, name, length)
	playlists(collection_id, likes_count)
	collections(collection_id, name, genre, creation_date)
	users(username, password, super_user)


	Relations:

	link_to_song(song_id, url)
	users_subscriptions(since, subscriber, subscribee)
	song_in_playlist(song_id, playlist_id)
	song_in_album(song_id, album_id)
	user_likes_playlist(collection_id, username)
	song_by_artist(song_id, artist_name)

