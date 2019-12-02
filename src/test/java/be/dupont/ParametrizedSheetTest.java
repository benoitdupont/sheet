package be.dupont;

import org.junit.Test;

import java.util.List;

import static be.dupont.SheetTest.BASIC_SHEET;
import static be.dupont.SheetTest.part;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParametrizedSheetTest {

    @Test
    public void validSheet1() {
        testIt(
                part(1, 2),
                part(3, 4)
        );
    }

    @Test
    public void validSheet2() {
        testIt(
                part(1, 10),
                part(1, 10),
                part(1, 10),
                part(1, 10),
                part(1, 10)
        );
    }

    @Test
    public void validSheet3() {
        testIt(
                part(10, 1),
                part(10, 1),
                part(10, 1),
                part(10, 1),
                part(10, 1)
        );
    }

    @Test
    public void validSheet4() {
        testIt(
                part(2, 2),
                part(3, 6),
                part(10, 1),
                part(3, 8)
        );
    }

    private void testIt(Part... partsszz) {
        List<Part> parts = asList(partsszz);
        Sheet newSheet = BASIC_SHEET.addParts2Sheet(parts);

        display(newSheet);

        assertEquals(parts.size(), newSheet.getParts().size());
        assertTrue("Sheet is invalid with positions", newSheet.isSheetValidWithPositions());
    }

    private void display(Sheet newSheet) {

//        for(Sheet.Position position: Sheet.getPositionsFrom(newSheet.getWidth(), newSheet.getLength(), 0, 0)){
        for (int i = 0; i < newSheet.getWidth(); i++) {

            System.out.print(i);
            for (int j = 0; j < newSheet.getLength(); j++) {
                Part part = newSheet.getParts().get(new Sheet.Position(i, j));
                if (part == null) {
                    System.out.print(" | ");
                } else {
                    System.out.print(" - ");
                }

            }

            System.out.println();
        }

    }


}
