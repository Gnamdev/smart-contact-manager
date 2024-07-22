package com.smartcontactmanager.Helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String content;
    @Builder.Default
    private MessageType type = MessageType.green;
}
