package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class MerchandiseDto {

    private Long id;
    private String merchandiseProductCode;
    private String merchandiseProductName;
    private int stockCount;
    private String photo;
    private Double price;
    private Long premiumPrice;
    private boolean premium;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MerchandiseDto)) return false;
        MerchandiseDto that = (MerchandiseDto) o;
        return stockCount == that.stockCount &&
            price == that.price &&
            premiumPrice == that.premiumPrice &&
            premium == that.premium &&
            id.equals(that.id) &&
            merchandiseProductCode.equals(that.merchandiseProductCode) &&
            merchandiseProductName.equals(that.merchandiseProductName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, merchandiseProductCode, merchandiseProductName, stockCount, price, premiumPrice, premium);
    }

    @Override
    public String toString() {
        return "MerchandiseDto{" +
            "id=" + id +
            ", merchandiseProductCode='" + merchandiseProductCode + '\'' +
            ", merchandiseProductName='" + merchandiseProductName + '\'' +
            ", stockCount=" + stockCount +
            ", price=" + price +
            ", premiumPrice=" + premiumPrice +
            ", premium=" + premium +
            '}';
    }
}
