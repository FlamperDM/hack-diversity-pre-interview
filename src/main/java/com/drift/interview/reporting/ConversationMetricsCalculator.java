package com.drift.interview.reporting;

import com.drift.interview.model.Conversation;
import com.drift.interview.model.ConversationResponseMetric;
import com.drift.interview.model.Message;

import java.util.List;

public class ConversationMetricsCalculator {
  public ConversationMetricsCalculator() {}


  /**
   * Returns a ConversationResponseMetric object which can be used to power data visualizations on the front end.
   */
  ConversationResponseMetric calculateAverageResponseTime(Conversation conversation) {
    List<Message> messages = conversation.getMessages();

    // implement me!

    long userFirstTime = 0;
    long average = 0;
    int countMsgAnswered = 0;

    boolean firstMsg = true;

    for (int i = 0; i < messages.size(); i++) {
      Message msg = messages.get(i);
      boolean isUser = !msg.isTeamMember();
      if (!isUser) continue;

      if (firstMsg && isUser)
        userFirstTime = msg.getCreatedAt();

      if (i + 1 < messages.size()){

        if (isUser == !messages.get(i + 1).isTeamMember()){
          firstMsg = false;
        } else {
          firstMsg = true;
          countMsgAnswered++;
          average += messages.get(i + 1).getCreatedAt() - userFirstTime;
          i++;
        }
      }
    }

    average /= countMsgAnswered;

    return ConversationResponseMetric.builder()
        .setConversationId(conversation.getId())
        .setAverageResponseMs(average)
        .build();
  }
}
