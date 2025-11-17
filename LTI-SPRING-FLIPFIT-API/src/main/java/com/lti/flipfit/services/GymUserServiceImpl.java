package com.lti.flipfit.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lti.flipfit.beans.GymUser;

@Service
public class GymUserServiceImpl implements GymUserService {

	List<GymUser> users = new ArrayList<>();
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public Boolean login(GymUser gymUser) {
		return users.stream()
				.filter(u -> u.getUserName().equalsIgnoreCase(gymUser.getUserName())
						&& passwordEncoder.matches(gymUser.getUserPassword(), u.getUserPassword()))
				.findAny().isPresent();
	}

	@Override
	public void register(GymUser gymUser) {
		gymUser.setUserPassword(passwordEncoder.encode(gymUser.getUserPassword()));
		users.add(gymUser);
	}

}
