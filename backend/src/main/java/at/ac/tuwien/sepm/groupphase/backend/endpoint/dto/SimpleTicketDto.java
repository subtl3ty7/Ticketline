package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
public class SimpleTicketDto {
    private Long ticketId;
    private String ticketCode;
    private boolean isPurchased;
    private boolean isReserved;
    private boolean isCancelled;
    private LocalDateTime purchaseDate;
    private Long seatId;
    private String userCode;
    private Integer price;
    private Long showId;
    private String eventName;
    private LocalDateTime showTime;

    public static final class SimpleTicketDtoBuilder {
        private Long ticketId;
        private String ticketCode;
        private boolean isPurchased;
        private boolean isReserved;
        private boolean isCancelled;
        private LocalDateTime purchaseDate;
        private Long seatId;
        private String userCode;
        private Integer price;
        private Long showId;
        private String eventName;
        private LocalDateTime showTime;

        public SimpleTicketDtoBuilder(Long ticketId, String ticketCode, boolean isPurchased, boolean isReserved, boolean isCancelled,
                                      LocalDateTime purchaseDate, Long seatId, String userCode, Integer price, Long showId,
                                      String eventName, LocalDateTime showTime) {
            this.ticketId = ticketId;
            this.ticketCode = ticketCode;
            this.isPurchased = isPurchased;
            this.isReserved = isReserved;
            this.isCancelled = isCancelled;
            this.purchaseDate = purchaseDate;
            this.seatId = seatId;
            this.userCode = userCode;
            this.price = price;
            this.showId = showId;
            this.eventName = eventName;
            this.showTime = showTime;
        }

        public static SimpleTicketDto.SimpleTicketDtoBuilder aSimpleTicketDto(Long ticketId, String ticketCode, boolean isPurchased,
                                                                              boolean isReserved, boolean isCancelled, LocalDateTime purchaseDate, Long seatId, String userCode,
                                                                              Integer price, Long showId, String eventName, LocalDateTime showTime) {
            return new SimpleTicketDto.SimpleTicketDtoBuilder(ticketId, ticketCode, isPurchased, isReserved, isCancelled, purchaseDate, seatId, userCode, price, showId, eventName, showTime);
        }

        public SimpleTicketDto build() {
            SimpleTicketDto simpleTicketDto = new SimpleTicketDto();
            simpleTicketDto.setTicketId(ticketId);
            simpleTicketDto.setTicketCode(ticketCode);
            simpleTicketDto.setPurchased(isPurchased);
            simpleTicketDto.setReserved(isReserved);
            simpleTicketDto.setCancelled(isCancelled);
            simpleTicketDto.setPurchaseDate(purchaseDate);
            simpleTicketDto.setSeatId(seatId);
            simpleTicketDto.setUserCode(userCode);
            simpleTicketDto.setPrice(price);
            simpleTicketDto.setShowId(showId);
            simpleTicketDto.setEventName(eventName);
            simpleTicketDto.setShowTime(showTime);
            new ArrayList<>();
            return simpleTicketDto;
        }
    }


}
