package Week8to11;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Testing Linear Search
class linearSearchTest {
    //    Declaring global variables
    private String[] list;
    private String hasData;
    private String hasNoData;
    private int returnedIndex;

    //    Setting declared arraylist
    @BeforeEach
    void setUp() {
        list = new String[] {"1","2","3","4","5","6"};
        hasData = "3";
        hasNoData = "8";
        returnedIndex = 2;
    }

    //    Dissolving arraylist
    @AfterEach
    void tearDown() {
        list = null;
    }

    //    Testing if linear search is in array with given data : returns index if found
    @Test
    void testRightInput() {
        assertEquals(Week8to11.linearSearch.getIndex(list,hasData),returnedIndex);
    }

    @Test
    void testWrongInput() {
        assertNotEquals(Week8to11.linearSearch.getIndex(list,hasNoData),returnedIndex);
    }
}