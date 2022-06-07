package response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferResponse {

    private Item item;

    private Store store;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("pickup_location")
    private PickupLocation pickupLocation;

    @JsonProperty("items_available")
    private int itemsAvailable;

    private double distance;

    private boolean favourite;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Item {

        @JsonProperty("item_id")
        private String itemId;

        @JsonProperty("price_excluding_taxes")
        private PriceDetails priceExcludingTaxes;

        @JsonProperty("price_including_taxes")
        private PriceDetails priceIncludingTaxes;

        @JsonProperty("value_excluding_taxes")
        private PriceDetails valueExcludingTaxes;

        @JsonProperty("value_including_taxes")
        private PriceDetails valueIncludingTaxes;

        @JsonProperty("item_category")
        private String itemCategory;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class PriceDetails{

            private String code;

            @JsonProperty("minor_units")
            private int minorUnits;

            @Override
            public String toString() {
                double price = minorUnits / 100.0;
                return price + code;
            }
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Rating{

            @JsonProperty("average_overall_rating")
            private double averageOverallRating;

            @JsonProperty("rating_count")
            private int ratingCount;

            @JsonProperty("month_count")
            private int monthCount;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Store {

        @JsonProperty("store_id")
        private String storeId;

        @JsonProperty("store_name")
        private String storeName;

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class PickupLocation {

        private Address address;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Address{

            @JsonProperty("address_line")
            private String addressLine;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OfferResponse that = (OfferResponse) o;

        if (itemsAvailable != that.itemsAvailable) return false;
        return item.equals(that.item);
    }

    @Override
    public int hashCode() {
        int result = item.hashCode();
        result = 31 * result + itemsAvailable;
        return result;
    }
}
