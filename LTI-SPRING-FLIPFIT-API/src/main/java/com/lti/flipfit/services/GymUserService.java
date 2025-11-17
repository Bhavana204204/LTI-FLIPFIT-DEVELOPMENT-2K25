package com.lti.flipfit.services;

import com.lti.flipfit.beans.GymUser;

public interface GymUserService {
	
	public Boolean login(GymUser gymUser);
	
	public void register(GymUser gymUser);


}
