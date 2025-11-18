package com.lti.flipfit.services;

import org.springframework.stereotype.Service;

import com.lti.flipfit.beans.GymUser;


@Service
public class WaitListServiceImpl implements WaitListService {

	@Override
    public GymUser promoteUser(GymUser gymUser) {
        System.out.println("Promoting user: " + gymUser.getUserName());
        return gymUser;
    }

}
