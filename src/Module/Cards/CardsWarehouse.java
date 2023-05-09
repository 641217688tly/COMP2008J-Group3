package Module.Cards;
import java.util.ArrayList;

public class CardsWarehouse()
{
    public ArrayList<Card> getCards()
    {
        var cardsWarehouse = new ArrayList<Card>();
        cardsWarehouse.add(new MoneyCard("1M"));
        cardsWarehouse.add(new MoneyCard("1M"));
        cardsWarehouse.add(new MoneyCard("1M"));
        cardsWarehouse.add(new MoneyCard("1M"));
        cardsWarehouse.add(new MoneyCard("1M"));
        cardsWarehouse.add(new MoneyCard("1M"));
        // 6 cards of 1M
        cardsWarehouse.add(new MoneyCard("2M"));
        cardsWarehouse.add(new MoneyCard("2M"));
        cardsWarehouse.add(new MoneyCard("2M"));
        cardsWarehouse.add(new MoneyCard("2M"));
        cardsWarehouse.add(new MoneyCard("2M"));
        // 5 cards of 2M
        cardsWarehouse.add(new MoneyCard("3M"));
        cardsWarehouse.add(new MoneyCard("3M"));
        cardsWarehouse.add(new MoneyCard("3M"));
        cardsWarehouse.add(new MoneyCard("3M"));
        // 4 cards of 3M
        cardsWarehouse.add(new MoneyCard("4M"));
        cardsWarehouse.add(new MoneyCard("4M"));
        cardsWarehouse.add(new MoneyCard("4M"));
        // 3 cards of 4M
        cardsWarehouse.add(new MoneyCard("5M"));
        cardsWarehouse.add(new MoneyCard("5M"));
        // 2 cards of 5M
        cardsWarehouse.add(new MoneyCard("10M"));
        // 1 cards of 10M
        cardsWarehouse.add(new PropertyCard("Blue"));
        cardsWarehouse.add(new PropertyCard("Blue"));
        // 2 cards of Blue
        cardsWarehouse.add(new PropertyCard("Brown"));
        cardsWarehouse.add(new PropertyCard("Brown"));
        // 2 cards of Brown
        cardsWarehouse.add(new PropertyCard("Utility"));
        cardsWarehouse.add(new PropertyCard("Utility"));
        // 2 cards of Utility
        cardsWarehouse.add(new PropertyCard("Green"));
        cardsWarehouse.add(new PropertyCard("Green"));
        cardsWarehouse.add(new PropertyCard("Green"));
        // 3 cards of Green
        cardsWarehouse.add(new PropertyCard("Yellow"));
        cardsWarehouse.add(new PropertyCard("Yellow"));
        cardsWarehouse.add(new PropertyCard("Yellow"));
        // 3 cards of Yellow
        cardsWarehouse.add(new PropertyCard("Red"));
        cardsWarehouse.add(new PropertyCard("Red"));
        cardsWarehouse.add(new PropertyCard("Red"));
        // 3 cards of Red
        cardsWarehouse.add(new PropertyCard("Orange"));
        cardsWarehouse.add(new PropertyCard("Orange"));
        cardsWarehouse.add(new PropertyCard("Orange"));
        // 3 cards of Orange
        cardsWarehouse.add(new PropertyCard("Pink"));
        cardsWarehouse.add(new PropertyCard("Pink"));
        cardsWarehouse.add(new PropertyCard("Pink"));
        // 3 cards of Pink
        cardsWarehouse.add(new PropertyCard("Light Blue"));
        cardsWarehouse.add(new PropertyCard("Light Blue"));
        cardsWarehouse.add(new PropertyCard("Light Blue"));
        // 3 cards of Light Blue
        cardsWarehouse.add(new PropertyCard("Railroad"));
        cardsWarehouse.add(new PropertyCard("Railroad"));
        cardsWarehouse.add(new PropertyCard("Railroad"));
        cardsWarehouse.add(new PropertyCard("Railroad"));
        // 4 cards of Light Railroad
        cardsWarehouse.add(new PropertyWildCard("Dark Blue/Green"));
        // 1 cards of Dark Blue/Green
        cardsWarehouse.add(new PropertyWildCard("Green/Railroad"));
        // 1 cards of Green/Railroad
        cardsWarehouse.add(new PropertyWildCard("Utility/Railroad"));
        // 1 cards of Utility/Railroad
        cardsWarehouse.add(new PropertyWildCard("LightBlue/Railroad"));
        // 1 cards of LightBlue/Railroad
        cardsWarehouse.add(new PropertyWildCard("Light Blue/Brown"));
        // 1 cards of Light Blue/Brown
        cardsWarehouse.add(new PropertyWildCard("Pink/Orange"));
        cardsWarehouse.add(new PropertyWildCard("Pink/Orange"));
        // 2 cards of Pink/Orange
        cardsWarehouse.add(new PropertyWildCard("Red/Yellow"));
        cardsWarehouse.add(new PropertyWildCard("Red/Yellow"));
        // 2 cards of PRed/Yellow
        cardsWarehouse.add(new PropertyWildCard("multi-colour Property Wildcards"));
        cardsWarehouse.add(new PropertyWildCard("multi-colour Property Wildcards"));
        // 2 cards of multi-colour Property Wildcards
        cardsWarehouse.add(new ActionCard("Deal Breaker"));
        cardsWarehouse.add(new ActionCard("Deal Breaker"));
        // 2 cards of multi-colour Property Wildcards
        cardsWarehouse.add(new ActionCard("Just Say No"));
        cardsWarehouse.add(new ActionCard("Just Say No"));
        cardsWarehouse.add(new ActionCard("Just Say No"));
        // 3 cards of Just Say No
        cardsWarehouse.add(new ActionCard("Force Deal"));
        cardsWarehouse.add(new ActionCard("Force Deal"));
        cardsWarehouse.add(new ActionCard("Force Deal"));
        cardsWarehouse.add(new ActionCard("Force Deal"));
        // 4 cards of Force Deal
        cardsWarehouse.add(new ActionCard("Debt Collector"));
        cardsWarehouse.add(new ActionCard("Debt Collector"));
        cardsWarehouse.add(new ActionCard("Debt Collector"));
        // 3 cards of Debt Collector
        cardsWarehouse.add(new ActionCard("It’s My Birthday"));
        cardsWarehouse.add(new ActionCard("It’s My Birthday"));
        cardsWarehouse.add(new ActionCard("It’s My Birthday"));
        // 3 cards of It’s My Birthday
        cardsWarehouse.add(new ActionCard("Pass Go"));
        cardsWarehouse.add(new ActionCard("Pass Go"));
        cardsWarehouse.add(new ActionCard("Pass Go"));
        cardsWarehouse.add(new ActionCard("Pass Go"));
        cardsWarehouse.add(new ActionCard("Pass Go"));
        cardsWarehouse.add(new ActionCard("Pass Go"));
        cardsWarehouse.add(new ActionCard("Pass Go"));
        cardsWarehouse.add(new ActionCard("Pass Go"));
        cardsWarehouse.add(new ActionCard("Pass Go"));
        cardsWarehouse.add(new ActionCard("Pass Go"));
        // 10 cards of Pass Go
        cardsWarehouse.add(new ActionCard("House"));
        cardsWarehouse.add(new ActionCard("House"));
        cardsWarehouse.add(new ActionCard("House"));
        // 3 cards of House
        cardsWarehouse.add(new ActionCard("Hotel"));
        cardsWarehouse.add(new ActionCard("Hotel"));
        cardsWarehouse.add(new ActionCard("Hotel"));
        // 3 cards of Hotel
        cardsWarehouse.add(new ActionCard("Double The Rent Cards"));
        cardsWarehouse.add(new ActionCard("Double The Rent Cards"));
        // 2 cards of Double The Rent Cards
        cardsWarehouse.add(new RentCard("Dark Blue/Green"));
        cardsWarehouse.add(new RentCard("Dark Blue/Green"));
        // 2 cards of Dark Blue/Green
        cardsWarehouse.add(new RentCard("Red/Yellow"));
        cardsWarehouse.add(new RentCard("Red/Yellow"));
        // 2 cards of Red/Yellow
        cardsWarehouse.add(new RentCard("Pink/Orange"));
        cardsWarehouse.add(new RentCard("Pink/Orange"));
        // 2 cards of Pink/Orange
        cardsWarehouse.add(new RentCard("Light Blue/Brown"));
        cardsWarehouse.add(new RentCard("Light Blue/Brown"));
        // 2 cards of Light Blue/Brown
        cardsWarehouse.add(new RentCard("Railroad/Utility"));
        cardsWarehouse.add(new RentCard("Railroad/Utility"));
        // 2 cards of Light Blue/Brown
        cardsWarehouse.add(new RentCard("Wild Rent"));
        cardsWarehouse.add(new RentCard("Wild Rent"));
        cardsWarehouse.add(new RentCard("Wild Rent"));
        // 3 cards of Wild Rent
        return cardsWarehouse;
    }

}