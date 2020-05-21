package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class SimpleTicketDto {
    private Long ticketId;
    private String ticketCode;
    private boolean isPurchased;
    private boolean isReserved;
    private LocalDateTime purchaseDate;
    private Long seatId;
    private String userCode;
    private Integer price;
    private Long showId;

    public static final class SimpleTicketDtoBuilder {
        private Long ticketId;
        private String ticketCode;
        private boolean isPurchased;
        private boolean isReserved;
        private LocalDateTime purchaseDate;
        private Long seatId;
        private String userCode;
        private Integer price;
        private Long showId;

        public SimpleTicketDtoBuilder(Long ticketId, String ticketCode, boolean isPurchased, boolean isReserved,
                                      LocalDateTime purchaseDate, Long seatId, String userCode, Integer price, Long showId) {
            this.ticketId = ticketId;
            this.ticketCode = ticketCode;
            this.isPurchased = isPurchased;
            this.isReserved = isReserved;
            this.purchaseDate = purchaseDate;
            this.seatId = seatId;
            this.userCode = userCode;
            this.price = price;
            this.showId = showId;
        }

        public static SimpleTicketDto.SimpleTicketDtoBuilder aSimpleTicketDto(Long ticketId, String ticketCode, boolean isPurchased,
                                                                              boolean isReserved, LocalDateTime purchaseDate, Long seatId, String userCode,
                                                                              Integer price, Long showId) {
            return new SimpleTicketDto.SimpleTicketDtoBuilder(ticketId, ticketCode, isPurchased, isReserved, purchaseDate, seatId, userCode, price, showId);
        }

        public SimpleTicketDto build() {
            SimpleTicketDto simpleTicketDto = new SimpleTicketDto();
            simpleTicketDto.setTicketId(ticketId);
            simpleTicketDto.setTicketCode(ticketCode);
            simpleTicketDto.setPurchased(isPurchased);
            simpleTicketDto.setReserved(isReserved);
            simpleTicketDto.setPurchaseDate(purchaseDate);
            simpleTicketDto.setSeatId(seatId);
            simpleTicketDto.setUserCode(userCode);
            simpleTicketDto.setPrice(price);
            simpleTicketDto.setShowId(showId);
            return simpleTicketDto;
        }
    }


}
