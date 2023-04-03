package Module.Cards;


public class PropertyCard extends Card {
    private int rentValue;
    private int houseValue;
    private int hotelValue;
    private boolean isMortgaged;

    public PropertyCard(String name, CardType cardType, int value, int rentValue, int houseValue, int hotelValue) {
        super(name, cardType);
        this.rentValue = rentValue;
        this.houseValue = houseValue;
        this.hotelValue = hotelValue;
        this.isMortgaged = false;
    }

    public int getRentValue() {
        return this.rentValue;
    }

    public int getHouseValue() {
        return this.houseValue;
    }

    public int getHotelValue() {
        return this.hotelValue;
    }

    public boolean isMortgaged() {
        return this.isMortgaged;
    }

    public void setMortgaged(boolean isMortgaged) {
        this.isMortgaged = isMortgaged;
    }

    @Override
    public void playCard(Player player, Player targetPlayer, Card targetCard) {
        player.addCard(this);
    }
}
