package be.dupont;

import org.junit.Test;

import java.util.Collections;

import static be.dupont.SheetTest.BASIC_SHEET;
import static be.dupont.SheetTest.part;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class ComputeSpaceLeftTest {

    @Test
    public void emptySheet() {
        Sheet newSheet = BASIC_SHEET.addParts2Sheet(Collections.emptyList());
        assertEquals(100, newSheet.getLostSpaceInSheet());
    }

    @Test
    public void fullSheet() {
        Sheet newSheet = BASIC_SHEET.addParts2Sheet(asList(part(10, 10)));
        assertEquals(0, newSheet.getLostSpaceInSheet());
    }

    @Test
    public void someSheet() {
        Sheet newSheet = BASIC_SHEET.addParts2Sheet(asList(part(5, 5)));
        assertEquals(75, newSheet.getLostSpaceInSheet());
    }

    @Test
    public void someThingOtherThanA1010(){
        Sheet sheet = new Sheet();

        sheet.setLength(4);
        sheet.setWidth(4);

        Sheet newSheet = sheet.addParts2Sheet(asList(part(2, 4)));
        assertEquals(50, newSheet.getLostSpaceInSheet());
    }

   
}
