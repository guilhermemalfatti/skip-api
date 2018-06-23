package com.br.code.skip.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.br.code.skip.SkipApplication;
import com.br.code.skip.db.MysqlDB;
import com.br.skip.client.SpotifyClient;
import com.br.skip.model.spotify.Query;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;

public class Controller {
	final static Logger logger = Logger.getLogger(SkipApplication.class);

	public static String storeSong(String id, String name) throws SQLException {
		Connection conn = MysqlDB.getConnection();
		if (conn != null) {
			int row = 0;
			try {
				String sql = "INSERT INTO SongsLine (songId, name) values (?, ?)";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, id);
				statement.setString(2, name);

				row = statement.executeUpdate();
				conn.close();
				logger.debug("Connection closed.");
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

			if (row > 0) {
				logger.debug("A song is added in the line.");
			} else {
				return "Data not inserted.";
			}

		}else {
			return "connection null, failed";
		}

		return "Success";
	}

	public static String removeSongByOrder() throws SQLException {
		Connection conn = MysqlDB.getConnection();
		if (conn != null) {
			int row = 0;
			try {
				String sql = "DELETE FROM SongsLine order by id LIMIT 1";
				PreparedStatement statement = conn.prepareStatement(sql);

				row = statement.executeUpdate();
				conn.close();
				logger.debug("Connection closed.");
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

			if (row > 0) {
				logger.debug("Song removed.");
			} else {
				return "Data not inserted.";
			}

		}

		return "Success";
	}
	
	public static String getAllSongs() throws SQLException {
		Connection conn = MysqlDB.getConnection();
		Map<String, String> songs = null;
		if (conn != null) {
			ResultSet rows = null;
			try {
				String sql = "Select * from SongsLine";
				PreparedStatement statement = conn.prepareStatement(sql);

				rows = statement.executeQuery();
				
				logger.debug("Connection closed.");
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

			songs = new HashMap<>();
			while (rows.next()) {
				songs.put(rows.getString("id"),rows.getString("name"));
			}
			conn.close();
		}else {
			return "connection null";
		}

		Gson gson = new Gson(); 
		String json = gson.toJson(songs); 
		return json;
	}
	
	public static String retriveSongsBySpotify(String query) throws JsonProcessingException {
		SpotifyClient client = new SpotifyClient();
		// TODO store the token somewhere, in order to avoid request token every time
		String token = client.getToken().getAccess_token();
		Query spotifyQuery = client.getTracksByQuery(query, token);

		if (spotifyQuery != null && spotifyQuery.getTracks() != null && spotifyQuery.getTracks().getItems() != null) {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			return ow.writeValueAsString(spotifyQuery.getTracks().getItems());
		} else {
			logger.debug("Song not found");
			return "not found";
		}
	}
}
