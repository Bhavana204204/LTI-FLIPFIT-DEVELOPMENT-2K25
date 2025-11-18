package com.lti.flipfit.services;
import com.lti.flipfit.beans.Notification;
import java.util.List;

public interface NotificationService {
	
   public Notification sendNotification(Notification notification);
   
   public List<Notification> getAllNotifications();
   
   public Notification getNotificationById(Long notificationId);
   
   public Notification deleteNotification(Long notificationId);
}