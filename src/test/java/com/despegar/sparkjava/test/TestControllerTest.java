package com.despegar.sparkjava.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.ClassRule;
import org.junit.Test;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;

import spark.servlet.SparkApplication;

/**
 * The test class
 * @author fwasy
 *
 */
public class TestControllerTest {

	public static class TestControllerTestSparkApplication implements SparkApplication {
		@Override
		public void init() {
			System.out.println("Test application initialized");
			new TestController(testServer.getInstance());
		}

		@Override
		public void destroy() {
			System.out.println("Test application stopped");
		}
		
		
	}
	
	@ClassRule
	public static SparkServer<TestControllerTestSparkApplication> testServer = new SparkServer<>(TestControllerTest.TestControllerTestSparkApplication.class, 4567);
	
	@Test
	public void test() throws Exception {
		GetMethod get = testServer.get("/test", false);
		get.addHeader("Test-Header", "test");
		HttpResponse httpResponse = testServer.execute(get);
		assertEquals(200, httpResponse.code());
		assertEquals("This works!", new String(httpResponse.body()));
		assertNotNull(testServer.getApplication());
	}
	
	
}