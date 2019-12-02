package be.dupont;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SheetTest {

    static final Part BASIC_PART = part();
    static final List<Part> PARTS = Collections.singletonList(BASIC_PART);
    static final Sheet BASIC_SHEET = getSheet();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validSheetWithOnePart() {
        Sheet newSheet = BASIC_SHEET.addParts2Sheet(PARTS);

        assertEquals(1, newSheet.getParts().size());
        assertTrue("Sheet is invalid", newSheet.isValid());
        assertTrue("Sheet is invalid with positions", newSheet.isSheetValidWithPositions());
    }

    @Test
    public void validSheetWithTwoParts() {
        Sheet newSheet = BASIC_SHEET.addParts2Sheet(asList(part(1, 2), part(3, 4)));

        assertEquals(2, newSheet.getParts().size());
        assertTrue("Sheet is invalid", newSheet.isValid());
        assertTrue("Sheet is invalid with positions", newSheet.isSheetValidWithPositions());
    }

    @Test
    public void validWithLength1() {
        Sheet newSheet = BASIC_SHEET.addParts2Sheet(asList(part(1, 2), part(10, 3)));

        assertEquals(2, newSheet.getParts().size());
        assertTrue("Sheet is invalid", newSheet.isValid());
        assertTrue("Sheet is invalid with positions", newSheet.isSheetValidWithPositions());
    }

    @Test
    public void invalidSheetWithLength() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Cannot add part, no position found");

        BASIC_SHEET.addParts2Sheet(asList(part(1, 10), part(10, 3)));
    }

    @Test
    public void invalidSheetWithWidth() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Cannot add part, no position found");

        BASIC_SHEET.addParts2Sheet(asList(part(1, 2), part(2, 12)));
    }

    static Part part(int length, int width) {
        Part part = new Part();
        part.setLength(length);
        part.setWidth(width);
        return part;
    }

    static Part part() {
        return part(1, 1);
    }

    private static Sheet getSheet() {
        Sheet sheet = new Sheet();

        sheet.setLength(10);
        sheet.setWidth(10);
        return sheet;
    }


}
