package com.mm.dev.ws.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mm.dev.entity.entity1.User;

@Service
public class StubHumanResourceService implements HumanResourceService {

	private final Logger logger = LoggerFactory.getLogger(StubHumanResourceService.class);

	@Override
	public User bookHoliday(Date startDate, Date endDate, String name) {
		User user = new User();
		user.setUsername("郭锋");
		this.logger.info("Booking holiday for [{} - {}] for [{}] ", startDate, endDate,
				name);
		return user;
	}

}