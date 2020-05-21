package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class DetailedTicketDto {
    private Long ticketId;
    private String ticketCode;
    private boolean isPurchased;
    private boolean isReserved;
    private LocalDateTime purchaseDate;
    private Seat seat;
    private String userCode;
    private Integer price;
    private Show show;

    public static final class DetailedTicketDtoBuilder{
        private Long ticketId;
        private String ticketCode;
        private boolean isPurchased;
        private boolean isReserved;
        private LocalDateTime purchaseDate;
        private Seat seat;
        private String userCode;
        private Integer price;
        private Show show;

        public DetailedTicketDtoBuilder(Long ticketId, String ticketCode, boolean isPurchased, boolean isReserved,
                                        LocalDateTime purchaseDate, Seat seat, String userCode, Integer price, Show show){
            this.ticketId = ticketId;
            this.ticketCode = ticketCode;
            this.isPurchased = isPurchased;
            this.isReserved = isReserved;
            this.purchaseDate =  purchaseDate;
            this.seat = seat;
            this.userCode = userCode;
            this.price = price;
            this.show = show;
        }

        public static DetailedTicketDto.DetailedTicketDtoBuilder aDetailedTicketDto(Long ticketId, String ticketCode, boolean isPurchased,
                                                                                    boolean isReserved, LocalDateTime purchaseDate, Seat seat, String userCode,
                                                                                    Integer price, Show show){
            return new DetailedTicketDto.DetailedTicketDtoBuilder(ticketId, ticketCode, isPurchased, isReserved, purchaseDate, seat, userCode, price, show);
        }

        public DetailedTicketDto build(){
            DetailedTicketDto detailedTicketDto = new DetailedTicketDto();
            detailedTicketDto.setTicketId(ticketId);
            detailedTicketDto.setTicketCode(ticketCode);
            detailedTicketDto.setPurchased(isPurchased);
            detailedTicketDto.setReserved(isReserved);
            detailedTicketDto.setPurchaseDate(purchaseDate);
            detailedTicketDto.setSeat(seat);
            detailedTicketDto.setUserCode(userCode);
            detailedTicketDto.setPrice(price);
            detailedTicketDto.setShow(show);
            return detailedTicketDto;
        }



    }
}
