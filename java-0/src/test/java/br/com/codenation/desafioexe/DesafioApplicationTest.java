package br.com.codenation.desafioexe;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;

public class DesafioApplicationTest {

	@Test
	public void fibonacci() {
		assertNotNull(DesafioApplication.fibonacci());
	}

	@Test
	public void isFibonacci() {
		assertTrue(DesafioApplication.isFibonacci(1));
	}

}
