package material.test.practica2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.practica2.GameOfThrones;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GameOfThronesTest {
    private GameOfThrones got;
    private String path;

    @BeforeEach
    void setUp() {
        got = new GameOfThrones();
        path = "C:/Users/borja/Desktop/URJC/Cuarto/Primer Cuatrimestre/EDA/Practicas/Practica2/GOT_Families.txt";
        try {
            got.loadFile(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testLoad(){
        got = new GameOfThrones();
        path = "C:/Users/borja/Desktop/URJC/Cuarto/Primer Cuatrimestre/EDA/Practicas/Practica2/GOT_Families.txt";
        try {
            got.loadFile(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testFindHeir() {
        String expected = "Eddard Stark";
        String output = got.findHeir("Stark");
        assertEquals(expected, output);
        assertNotEquals("Cersei Lannister",got.findHeir("Lannister"));
    }

    @Test
    void testChangeFamily() {
        got.changeFamily("Daenerys Targaryen", "Eddard Stark");
        assertTrue(got.areFamily("Daenerys Targaryen", "Arya Stark"));
    }

    @Test
    void testAreFamily() {
        assertTrue(got.areFamily("Arya Stark", "Robb Stark"));
        assertFalse(got.areFamily("Catelyn Tully", "Aerys Targaryen"));
    }

}