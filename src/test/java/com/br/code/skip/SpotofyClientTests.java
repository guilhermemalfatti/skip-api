package com.br.code.skip;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.br.skip.client.SpotifyClient;

@RunWith(MockitoJUnitRunner.class)
public class SpotofyClientTests {
	@Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SpotifyClient sClient;
    
    @Test
	public void contextLoads() {

    	//TOOD

	}
}
