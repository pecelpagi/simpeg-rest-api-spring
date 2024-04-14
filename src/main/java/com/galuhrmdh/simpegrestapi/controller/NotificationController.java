package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.PagingResponse;
import com.galuhrmdh.simpegrestapi.model.WebResponse;
import com.galuhrmdh.simpegrestapi.model.employee.EmployeeResponse;
import com.galuhrmdh.simpegrestapi.model.notification.NotificationResponse;
import com.galuhrmdh.simpegrestapi.service.EmployeeService;
import com.galuhrmdh.simpegrestapi.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @GetMapping(
            path = "/api/notifications",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<NotificationResponse>> list(User user,
                                                    @RequestParam(value = "search", required = false) String search,
                                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        ListRequest request = ListRequest.builder()
                .search(search)
                .page(page)
                .size(size)
                .build();

        Page<NotificationResponse> notificationResponses = notificationService.list(request);
        return WebResponse.<List<NotificationResponse>>builder()
                .data(notificationResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(notificationResponses.getNumber())
                        .totalPage(notificationResponses.getTotalPages())
                        .size(notificationResponses.getSize())
                        .totalElements(notificationResponses.getTotalElements())
                        .build())
                .build();
    }

    @GetMapping(
            path = "/api/unread_notification_count",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Long> getUnreadNotification(User user) {
        Long unreadNotificationCount = notificationService.getUnreadNotificationCount();

        return WebResponse.<Long>builder().data(unreadNotificationCount).build();
    }

    @PostMapping(
            path = "/api/read_notification",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> updateUnreadNotification(User user) {
        notificationService.updateReadStatusAsRead();

        return WebResponse.<String>builder().data("OK").build();
    }


}
