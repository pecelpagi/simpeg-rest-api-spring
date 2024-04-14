package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.entity.Notification;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.employee.EmployeeResponse;
import com.galuhrmdh.simpegrestapi.model.notification.CreateNotificationRequest;
import com.galuhrmdh.simpegrestapi.model.notification.NotificationResponse;
import com.galuhrmdh.simpegrestapi.repository.NotificationRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public SavedResponse create(CreateNotificationRequest request) {
        Notification notification = new Notification();

        System.out.println("NOTIFICATION_TYPE: " + request.getNotificationType());

        notification.setNotificationType(request.getNotificationType());
        notification.setAttachment(request.getAttachment());
        notification = notificationRepository.save(notification);

        return SavedResponse.builder()
                .id(notification.getId())
                .label(notification.getAttachment())
                .build();
    }

    private NotificationResponse toNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .notificationType(notification.getNotificationType())
                .attachment(notification.getAttachment())
                .createdAt(notification.getCreatedAt())
                .readStatus(notification.getReadStatus())
                .build();
    }


    @Transactional(readOnly = true)
    public Page<NotificationResponse> list(ListRequest request) {
        Specification<Notification> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(request.getSearch())) {
                predicates.add(builder.or(
                        builder.like(root.get("attachment"), "%" + request.getSearch() + "%")
                ));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Notification> notifications = notificationRepository.findAll(specification, pageable);

        List<NotificationResponse> notificationResponses = notifications.stream().map(this::toNotificationResponse).toList();

        return new PageImpl<>(notificationResponses, pageable, notifications.getTotalElements());
    }

    public long getUnreadNotificationCount() {
        return notificationRepository.countByReadStatus(Boolean.FALSE);
    }

    @Transactional
    public void updateReadStatusAsRead() {
        notificationRepository.updateReadStatusAsRead();
    }

}
