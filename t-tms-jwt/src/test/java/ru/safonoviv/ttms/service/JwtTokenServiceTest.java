package ru.safonoviv.ttms.service;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenServiceTest {
	@Autowired
	private JwtTokenService jwtTokenService;

	@Test
	void test() {
		String token1 ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBRE1JTiIsIlVTRVIiXSwic3ViIjoibWFpbF8xIiwiaWF0IjoxNzAyNTYxMzc3LCJleHAiOjE3MDMxNjEzNzd9.UxIrxRLaSe2SbrWfEsktn_-RelqAWdqWlHzjGxST5RM";
		String token2 ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBRE1JTiJdLCJzdWIiOiJtYWlsXzIiLCJpYXQiOjE3MDI1NjE0MTMsImV4cCI6MTcwMzE2MTQxM30.SJgbbHxLjQzw_2rgTi4Fw7erlF3AlsQXnd97GywE1H4";
//		Mockito.when(jwtTokenService.getSignInKey()).thenReturn(Keys.hmacShaKeyFor(Decoders.BASE64.decode("DDBCADF2481C76F86A1EAC7DBF1AEDDBCADF2481C76F86A1EAC7DBF1AE")));
		
		Assertions.assertEquals(jwtTokenService.getUsername(token1), "mail_1");
		Assertions.assertEquals(jwtTokenService.getRoles(token1), Arrays.asList("ADMIN","USER"));
		
		Assertions.assertEquals(jwtTokenService.getUsername(token2), "mail_2");
		Assertions.assertEquals(jwtTokenService.getRoles(token2), Arrays.asList("ADMIN"));
	}

}
