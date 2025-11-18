
package com.lti.flipfit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import com.lti.flipfit.beans.Notification;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final List<Notification> notifications = new ArrayList<>();

    public NotificationServiceImpl() {
        
        Notification sample = new Notification();
        sample.setNotificationId(1L);
        sample.setUserId(101L);
        sample.setMessage("Your booking is successful");
        sample.setStatus("SENT");
        notifications.add(sample);
    }

    @Override
    public Notification sendNotification(Notification notification) {
        notifications.add(notification);
        return notification;
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notifications;
    }

    @Override
    public Notification getNotificationById(Long notificationId) {
        return notifications.stream()
                .filter(n -> Objects.equals(n.getNotificationId(), notificationId))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
    }

    @Override
    public Notification deleteNotification(Long notificationId) {
        Notification notification = getNotificationById(notificationId);
        notifications.remove(notification);
        return notification;
    }
}
