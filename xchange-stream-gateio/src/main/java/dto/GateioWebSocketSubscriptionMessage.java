package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.knowm.xchange.currency.CurrencyPair;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/** Author: Max Gao (gaamox@tutanota.com) Created: 05-05-2021 */
@Setter
@NoArgsConstructor
public class GateioWebSocketSubscriptionMessage {
  @JsonProperty("time")
  private int time;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("event")
  private String event;

  @JsonProperty("payload")
  private String[] payload;

  public GateioWebSocketSubscriptionMessage(
      String channelName, CurrencyPair currencyPair, Integer interval) {}

  public GateioWebSocketSubscriptionMessage(
          String channelName,
          String event,
          CurrencyPair currencyPair,
          Integer interval,
          Integer depth) {
    this.time = (int) (Instant.now().getEpochSecond());
    this.channel = channelName;
    this.event = event;
    this.payload =
            Arrays.asList(
                            currencyPair.toString().replace('/', '_'),
                            (depth != null && channelName.contains("order_book")) ? Integer.toString(depth) : null,
                            (interval != null  && channelName.contains("order_book")) ? interval + "ms" : null)
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList())
                    .toArray(new String[] {});
  }

  public GateioWebSocketSubscriptionMessage(
          String channelName,
          String event,
          Set<CurrencyPair> pairs) {
    this.time = (int) (Instant.now().getEpochSecond());
    this.channel = channelName;
    this.event = event;
    this.payload = pairs.stream()
            .map(CurrencyPair::toString)
            .map(name -> name.replace('/', '_'))
            .collect(Collectors.toList())
            .toArray(new String[]{});
  }
}
