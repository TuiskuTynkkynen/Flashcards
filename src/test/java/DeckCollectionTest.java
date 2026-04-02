import fi.jyu.ohj2.tynkottu.muistikortit.model.DeckCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckCollectionTest {
    @Test
    void addTest_addsDeck(){
        DeckCollection collection = new DeckCollection("test.json");

        String str = "Foo";
        collection.addDeck(str, str);

        assertEquals(1, collection.size());
        assertEquals(str, collection.getSelectedDeck().getTitle());
        assertEquals(str, collection.getSelectedDeck().getDescription());
    }

    @Test
    void addTest_selectsDeck(){
        DeckCollection collection = new DeckCollection("test.json");

        String str1 = "Foo";
        collection.addDeck(str1, str1);

        assertEquals(str1, collection.getSelectedDeck().getTitle());
        assertEquals(str1, collection.getSelectedDeck().getDescription());

        String str2 = "Bar";
        collection.addDeck(str2, str2);

        assertEquals(str2, collection.getSelectedDeck().getTitle());
        assertEquals(str2, collection.getSelectedDeck().getDescription());
    }

    @Test
    void addTest_addsDuplicates(){
        DeckCollection collection = new DeckCollection("test.json");

        String str = "Foo";
        collection.addDeck(str, str);
        collection.addDeck(str, str);

        assertEquals(2, collection.size());
    }

    @Test
    void addTest_trimsTitleAndDescription(){
        DeckCollection collection = new DeckCollection("test.json");

        String str = " Foo      ";
        collection.addDeck(str, str);

        assertEquals(str.trim(), collection.getSelectedDeck().getTitle());
    }

    @Test
    void removeTest_removesDeck(){
        DeckCollection collection = new DeckCollection("test.json");

        collection.addDeck("Foo", "Foo");
        collection.removeDeck(collection.getSelectedDeck());

        assertEquals(0, collection.size());
    }

    @Test
    void removeTest_selectsDeck(){
        DeckCollection collection = new DeckCollection("test.json");

        String str1 = "Foo", str2 = "Bar";
        collection.addDeck(str1, str1);
        collection.addDeck(str2, str2);

        assertEquals(str2, collection.getSelectedDeck().getTitle());
        assertEquals(str2, collection.getSelectedDeck().getDescription());

        collection.removeDeck(collection.getSelectedDeck());

        assertEquals(str1, collection.getSelectedDeck().getTitle());
        assertEquals(str1, collection.getSelectedDeck().getDescription());
    }

    @Test
    void serializationTest_serializesAndDeserializesDecks(){
        String str = "Foo";

        {
            DeckCollection collection = new DeckCollection("test.json");
            collection.addDeck(str, str);
            collection.addDeck(str, str);
            collection.addDeck(str, str);
            collection.serialize();
        }

        DeckCollection collection = new DeckCollection("test.json");
        collection.deserialize();

        assertEquals(3, collection.size());
        assertEquals(str, collection.getSelectedDeck().getTitle());
        assertEquals(str, collection.getSelectedDeck().getDescription());
    }

    @Test
    void serializationTest_serializesAndDeserializesSelection(){
        String str = "Foo";

        {
            DeckCollection collection = new DeckCollection("test.json");
            collection.addDeck("Bar", "Bar");
            collection.addDeck(str, str);
            collection.addDeck("Baz", "Baz");

            collection.setSelectedDeckIndex(1);
            assertEquals(str, collection.getSelectedDeck().getTitle());
            assertEquals(str, collection.getSelectedDeck().getDescription());

            collection.serialize();
        }

        DeckCollection collection = new DeckCollection("test.json");
        collection.deserialize();

        assertEquals(str, collection.getSelectedDeck().getTitle());
        assertEquals(str, collection.getSelectedDeck().getDescription());
    }

    @Test
    void selectionTest_selectIndex(){
        DeckCollection collection = new DeckCollection("test.json");

        String str1 = "Foo";
        String str2 = "Bar";
        collection.addDeck(str1, str1);
        collection.addDeck(str2, str2);

        collection.setSelectedDeckIndex(0);
        assertEquals(str1, collection.getSelectedDeck().getTitle());
        assertEquals(str1, collection.getSelectedDeck().getDescription());

        collection.setSelectedDeckIndex(1);
        assertEquals(str2, collection.getSelectedDeck().getTitle());
        assertEquals(str2, collection.getSelectedDeck().getDescription());


        collection.setSelectedDeckIndex(100);
        assertNotNull(collection.getSelectedDeck());
    }

    @Test
    void selectionTest_selectNext(){
        DeckCollection collection = new DeckCollection("test.json");

        String str1 = "Foo";
        String str2 = "Bar";
        collection.addDeck(str1, str1);
        collection.addDeck(str2, str2);

        collection.setSelectedDeckIndex(0);
        collection.selectNext();
        assertEquals(str2, collection.getSelectedDeck().getTitle());
        assertEquals(str2, collection.getSelectedDeck().getDescription());

        collection.selectNext();
        assertNotNull(collection.getSelectedDeck());
    }

    @Test
    void selectionTest_selectPrevious(){
        DeckCollection collection = new DeckCollection("test.json");

        String str1 = "Foo";
        String str2 = "Bar";
        collection.addDeck(str1, str1);
        collection.addDeck(str2, str2);

        assertEquals(str2, collection.getSelectedDeck().getTitle());
        assertEquals(str2, collection.getSelectedDeck().getDescription());

        collection.selectPrevious();
        assertEquals(str1, collection.getSelectedDeck().getTitle());
        assertEquals(str1, collection.getSelectedDeck().getDescription());

        collection.selectPrevious();
        assertNotNull(collection.getSelectedDeck());
    }

    @Test
    void selectionTest_getOffsetDeck(){
        DeckCollection collection = new DeckCollection("test.json");

        String str1 = "Foo";
        String str2 = "Bar";
        String str3 = "Baz";
        collection.addDeck(str1, str1);
        collection.addDeck(str2, str2);
        collection.addDeck(str3, str3);

        collection.setSelectedDeckIndex(1);

        assertEquals(str1, collection.getOffsetDeck(-1).getTitle());
        assertEquals(str1, collection.getOffsetDeck(-1).getDescription());

        assertEquals(str3, collection.getOffsetDeck(1).getTitle());
        assertEquals(str3, collection.getOffsetDeck(1).getDescription());

        assertNull(collection.getOffsetDeck(-100));
        assertNull(collection.getOffsetDeck(100));
    }
}
