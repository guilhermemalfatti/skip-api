package com.br.skip.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;

import com.br.skip.model.spotify.Query;
import com.br.skip.model.spotify.Token;

import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class SpotifyClient {
	private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
	private static final String SEARCH_URL = "https://api.spotify.com/v1/search";

	private static final String CLIENT_ID = System.getenv("CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");
	
	public Token getToken() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(CLIENT_ID, CLIENT_SECRET));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "client_credentials");
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<Token> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, Token.class);

		//TODO store the token
		return response.getBody();
	}
	
	public Query getTracksByQuery(String query, String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(SEARCH_URL);

		builder.queryParam("q", query);
		builder.queryParam("type", "track");
		builder.queryParam("limit", 10);
		
		ResponseEntity<Query> response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, entity, com.br.skip.model.spotify.Query.class);
				
		return response.getBody();
	}
}
