package be.dupont;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntersectTest {

    @Test
    public void isPartValidInSheetGeneric(){
        assertTrue(Sheet.isPartValidInSheet(new Sheet.Position(0,0), new Part(10, 10), 10, 10));
        assertTrue(Sheet.isPartValidInSheet(new Sheet.Position(9,0), new Part(10, 1), 10, 10));
    }

    @Test
    public void isPartValidInSheetWidth(){
        assertTrue(Sheet.isPartValidInSheet(new Sheet.Position(9,0), new Part(10, 1), 10, 10));
    }

    @Test
    public void isPartValidInSheetLength(){
        assertTrue(Sheet.isPartValidInSheet(new Sheet.Position(0,9), new Part(1, 10), 10, 10));
    }


    @Test
    public void intersectTrue() {
        assertTrue(Sheet.intersect(
                new Sheet.Position(0, 0), new Part(2, 2),
                new Sheet.Position(0, 1), new Part(2, 2)));
    }


    @Test
    public void intersectFalse() {
        assertFalse(Sheet.intersect(
                new Sheet.Position(0, 0), new Part(1, 1),
                new Sheet.Position(2, 2), new Part(2, 2)));
    }

    // Let's stay that this intersect because we want a value of 1 unity (1cm) between two piececs (width of the blade)
    @Test
    public void intersectFalseNextToTheOther() {
        assertTrue(Sheet.intersect(
                new Sheet.Position(0, 0), new Part(2, 2),
                new Sheet.Position(2, 0), new Part(2, 2)));
    }

    @Test
    public void getPositionsFrom1() {
        assertEquals(4, Sheet.getPositionsFrom(1, 1, 0, 0).size());
    }

    @Test
    public void getPositionsFrom2() {
        assertEquals(9, Sheet.getPositionsFrom(2, 2, 0, 0).size());
    }

}
