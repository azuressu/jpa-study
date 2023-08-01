package me.whitebear.jpastudy.thread;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ThreadSearchCond {

    private Long channelId; // 채널 Id
    private Long mentionedUserId; // 멘션된 유저의 Id;

}
