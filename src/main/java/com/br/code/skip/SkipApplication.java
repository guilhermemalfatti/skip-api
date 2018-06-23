package com.br.code.skip;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import org.apache.log4j.Logger;

import com.br.code.skip.manager.Controller;

public class SkipApplication {
	final static Logger logger = Logger.getLogger(SkipApplication.class);

	public static void main(String[] args) {
		port(8080);
		get("/retriveSongs/:query", (req, res) -> {
			return Controller.retriveSongsBySpotify(req.params("query"));
		});

		post("/storeListSongs/:id/:name", (req, res) -> {
			return Controller.storeSong(req.params("id"), req.params("name"));
		});

		get("/removeSong", (req, res) -> {
			return Controller.removeSongByOrder();
		});

		get("/getSongs", (req, res) -> {
			return Controller.getAllSongs();
		});
	}

}
