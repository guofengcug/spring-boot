package com.mm.dev.ws.service;

import java.util.Date;

import com.mm.dev.entity.entity1.User;

public interface HumanResourceService {

	User bookHoliday(Date startDate, Date endDate, String name);

}