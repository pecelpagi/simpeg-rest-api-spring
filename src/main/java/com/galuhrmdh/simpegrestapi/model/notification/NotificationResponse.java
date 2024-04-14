package com.galuhrmdh.simpegrestapi.model.notification;

import com.galuhrmdh.simpegrestapi.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {

    private Integer id;

    private NotificationType notificationType;

    private String attachment;

    private Date createdAt;

    private Boolean readStatus;
}
