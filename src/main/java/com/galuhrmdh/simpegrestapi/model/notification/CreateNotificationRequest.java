package com.galuhrmdh.simpegrestapi.model.notification;

import com.galuhrmdh.simpegrestapi.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateNotificationRequest {

    private NotificationType notificationType;

    private String attachment;

}
