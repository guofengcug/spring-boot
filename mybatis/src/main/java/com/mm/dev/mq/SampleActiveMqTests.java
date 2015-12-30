package com.mm.dev.mq;

import static org.junit.Assert.assertTrue;

import javax.jms.JMSException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.OutputCapture;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mm.dev.DevApplication;

/**
 * Integration tests for demo application.
 *
 * @author Eddú Meléndez
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevApplication.class)
public class SampleActiveMqTests {

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Autowired
	private Producer producer;

	@Test
	public void sendSimpleMessage() throws InterruptedException, JMSException {
		this.producer.send("Test message");
		Thread.sleep(1000L);
		assertTrue(this.outputCapture.toString().contains("Test message"));
	}

}