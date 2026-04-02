import fi.jyu.ohj2.tynkottu.muistikortit.model.Card;
import fi.jyu.ohj2.tynkottu.muistikortit.model.Deck;
import fi.jyu.ohj2.tynkottu.muistikortit.model.DeckCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    @Test
    void addTest_addsCard(){
        Deck deck = new Deck();

        String str = "Foo";
        deck.addCard(str, str);

        assertEquals(1, deck.size());
        assertEquals(str, deck.getCards().getFirst().getTitle());
        assertEquals(str, deck.getCards().getFirst().getDescription());
    }

    @Test
    void addTest_addsDuplicates(){
        Deck deck = new Deck();

        String str = "Foo";
        deck.addCard(str, str);
        deck.addCard(str, str);

        assertEquals(2, deck.size());
    }


    @Test
    void addTest_trimsTitleAndDescription(){
        Deck deck = new Deck();

        String str = " Foo      ";
        deck.addCard(str, str);

        assertEquals(str.trim(), deck.getCards().getFirst().getTitle());
    }


    @Test
    void removeTest_removesDeck(){
        Deck deck = new Deck();

        deck.addCard("Foo", "Foo");
        deck.removeCard(deck.getCards().getFirst());

        assertEquals(0, deck.size());
    }


    @Test
    void selectionTest_selectIndex(){
        Deck deck = new Deck();

        String str1 = "Foo";
        String str2 = "Bar";
        deck.addCard(str1, str1);
        deck.addCard(str2, str2);

        deck.setSelectedCardIndex(0);
        assertEquals(str1, deck.getSelectedCard().getTitle());
        assertEquals(str1, deck.getSelectedCard().getDescription());

        deck.setSelectedCardIndex(1);
        assertEquals(str2, deck.getSelectedCard().getTitle());
        assertEquals(str2, deck.getSelectedCard().getDescription());


        deck.setSelectedCardIndex(100);
        assertNotNull(deck.getSelectedCard());
    }

    @Test
    void selectionTest_selectNext(){
        Deck deck = new Deck();

        String str1 = "Foo";
        String str2 = "Bar";
        deck.addCard(str1, str1);
        deck.addCard(str2, str2);

        deck.setSelectedCardIndex(0);
        assertEquals(str1, deck.getSelectedCard().getTitle());
        assertEquals(str1, deck.getSelectedCard().getDescription());

        deck.selectNext();
        assertEquals(str2, deck.getSelectedCard().getTitle());
        assertEquals(str2, deck.getSelectedCard().getDescription());

        deck.selectNext();
        assertNotNull(deck.getSelectedCard());
    }

    @Test
    void selectionTest_selectPrevious(){
        Deck deck = new Deck();

        String str1 = "Foo";
        String str2 = "Bar";
        deck.addCard(str1, str1);
        deck.addCard(str2, str2);

        deck.setSelectedCardIndex(1);
        assertEquals(str2, deck.getSelectedCard().getTitle());
        assertEquals(str2, deck.getSelectedCard().getDescription());

        deck.selectPrevious();
        assertEquals(str1, deck.getSelectedCard().getTitle());
        assertEquals(str1, deck.getSelectedCard().getDescription());

        deck.selectPrevious();
        assertNotNull(deck.getSelectedCard());
    }

    @Test
    void shuffleTest_shufflesCards(){
        Deck deck = new Deck();

        for(int i = 0; i < 100; i++){
            deck.addCard("card " + i, "");
        }

        boolean isSame = true;
        Card first = deck.getCards().getFirst();
        Card last = deck.getCards().getLast();

        // Shuffles cards 100 times
        // Test that the first and last cards are not the same every time
        for(int i = 0; i < 100 && isSame; i++){
            deck.shuffle();

            isSame = (first != deck.getCards().getFirst());
            isSame &= (last != deck.getCards().getLast());
        }

        assertFalse(isSame);
    }

    @Test
    void shuffleTest_resetsSelection(){
        Deck deck = new Deck();

        String str1 = "Foo";
        String str2 = "Bar";
        deck.addCard(str1, str1);
        deck.addCard(str2, str2);

        deck.setSelectedCardIndex(1);
        assertEquals(1, deck.getSelectedCardIndex());

        deck.shuffle();
        assertEquals(0, deck.getSelectedCardIndex());
    }

    @Test
    void serializationTest_serializeAndDeserializesCards(){
        String str1 = "Foo";
        String str2 = "Bar";
        {
            DeckCollection collection = new DeckCollection("test.json");
            collection.addDeck("", "");

            Deck deck = collection.getSelectedDeck();
            deck.addCard(str1, str1);
            deck.addCard(str2, str2);

            collection.serialize();
        }

        DeckCollection collection = new DeckCollection("test.json");
        collection.deserialize();

        Deck deck = collection.getSelectedDeck();

        deck.setSelectedCardIndex(0);
        assertEquals(str1, deck.getSelectedCard().getTitle());
        assertEquals(str1, deck.getSelectedCard().getDescription());

        deck.setSelectedCardIndex(1);
        assertEquals(str2, deck.getSelectedCard().getTitle());
        assertEquals(str2, deck.getSelectedCard().getDescription());
    }
}
