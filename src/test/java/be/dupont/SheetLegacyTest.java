package be.dupont;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

@Ignore
public class SheetLegacyTest {
    @Test
    public void noCompositionPossible() {
        // given some parts, try to put them into an efficient repartition in the base sheet

        // set dimensions of base panel
        int[] baseSheet = new int[2];
        baseSheet[0] = 100;
        baseSheet[1] = 100;

        // All elements that we try to put into the sheet
        int[][] sheetParts = new int[2][2];
        sheetParts[0] = new int[]{90, 90};
        sheetParts[1] = new int[]{90, 90};

        assertNull(arrangeMagically(baseSheet, sheetParts));
    }

    @Test
    public void arrangeMagically() {
        // given some parts, try to put them into an efficient repartition in the base sheet

        // set dimensions of base panel
        int[] baseSheet = new int[2];
        baseSheet[0] = 100;
        baseSheet[1] = 100;

        // All elements that we try to put into the sheet
        int[][] sheetParts = new int[2][2];
        sheetParts[0] = new int[]{40, 20};
        sheetParts[1] = new int[]{70, 20};

        int[][] sheetComposition = arrangeMagically(baseSheet, sheetParts);
        if(sheetComposition == null){
            Assert.fail("No composition possible");
        }

        displayTable(sheetComposition);

        assertTrue(isValid(baseSheet, sheetComposition));
    }

    /**
     * Given a base sheet and parts, try to put them into any arrangements.
     *
     * @return null if no magic composition found.
     */
    private int[][] arrangeMagically(int[] baseSheet, int[][] sheetParts) {

        List<int[][]> sheetComposition = getSheetCompoCandidates(baseSheet, sheetParts);

        for(int[][] candidate : sheetComposition) {
            if (isValid(baseSheet, candidate)) {
                return candidate;
            }
        }

        return null;
    }

    private List<int[][]> getSheetCompoCandidates(int[] baseSheet, int[][] sheetParts) {

        // try all combinations, here at least each piece should be added horizontally or vertically

        //
//        sheetParts[0] = new int[]{40, 20};
//        sheetParts[1] = new int[]{70, 20};

        // 40 20 + 70 20
        // 20 40 + 70 20
        // 40 20 + 20 70
        // 20 40 + 20 70

        // for each part,
        for(int i = 0; i < sheetParts.length ; i ++){
            // add all other parts, me not reversed
            for(int j = i + 1; j < sheetParts.length -1 ; j++){

            }


            // add all other parts, me revered
        }


        return Collections.singletonList(getSheetCompoCandidate(baseSheet, sheetParts));
    }
        /**
         * Can return invalid compositions
         */
    private int[][] getSheetCompoCandidate(int[] baseSheet, int[][] sheetParts) {


        return sheetParts;
    }

    @Test
    public void computeLostWood() {
        // compute all wood that has been lost (percentage ?)
    }


    @Test
    public void validWithOnePart() {
        int[][] sheetComposition = new int[5][2];

        // set dimensions of base panel
        int[] baseSheet = new int[2];
        baseSheet[0] = 100;
        baseSheet[1] = 100;

        // add an element of size 10;10
        sheetComposition[0] = new int[]{10, 10};

        displayTable(sheetComposition);

        assertTrue(isValid(baseSheet, sheetComposition));
    }

    @Test
    public void validWithMultipleParts() {
        int[][] sheetComposition = new int[5][2];

        // set dimensions of base panel
        int[] baseSheet = new int[2];
        baseSheet[0] = 100;
        baseSheet[1] = 100;

        // add an element of size 10;10
        sheetComposition[0] = new int[]{10, 10};
        sheetComposition[1] = new int[]{10, 10};
        sheetComposition[2] = new int[]{10, 10};
        sheetComposition[3] = new int[]{10, 10};

        displayTable(sheetComposition);

        assertTrue(isValid(baseSheet, sheetComposition));
    }

    @Test
    public void invalidLenghtWithOnePart() {
        int[][] sheetComposition = new int[5][2];

        // set dimensions of base panel
        int[] baseSheet = new int[2];
        baseSheet[0] = 100;
        baseSheet[1] = 100;

        // add an element of size 10;10
        sheetComposition[0] = new int[]{1000, 10};

        displayTable(sheetComposition);

        assertFalse(isValid(baseSheet, sheetComposition));
    }

    @Test
    public void invalidLenghtWithMultipleParts() {
        int[][] sheetComposition = new int[5][2];

        // set dimensions of base panel
        int[] baseSheet = new int[2];
        baseSheet[0] = 100;
        baseSheet[1] = 100;

        // add an element of size 10;10
        sheetComposition[0] = new int[]{90, 10};
        sheetComposition[1] = new int[]{10, 10};

        displayTable(sheetComposition);

        assertFalse(isValid(baseSheet, sheetComposition));
    }

    @Test
    public void invalidWidthWithOnePart() {
        int[][] sheetComposition = new int[5][2];

        // set dimensions of base panel
        int[] baseSheet = new int[2];
        baseSheet[0] = 100;
        baseSheet[1] = 100;

        // add an element of size 10;10
        sheetComposition[0] = new int[]{10, 1000};

        displayTable(sheetComposition);

        assertFalse(isValid(baseSheet, sheetComposition));
    }

    @Test
    public void invalidWidthWithMultipleParts() {
        int[][] sheetComposition = new int[5][2];

        // set dimensions of base panel
        int[] baseSheet = new int[2];
        baseSheet[0] = 100;
        baseSheet[1] = 100;

        // add an element of size 10;10
        sheetComposition[0] = new int[]{10, 50};
        sheetComposition[1] = new int[]{10, 40};
        sheetComposition[2] = new int[]{10, 20};

        displayTable(sheetComposition);

        assertFalse(isValid(baseSheet, sheetComposition));
    }

    private void displayTable(int[][] sheetComposition) {
        for (int i = 0; i < sheetComposition.length; i++) {
            System.out.println(Arrays.toString(sheetComposition[i]));
        }
    }

    /**
     * A sheet is valid iff the size of it's component is lower than the base size
     */
    private boolean isValid(int[] baseSheet, int[][] sheetComposition) {
        int length = baseSheet[0];
        int width = baseSheet[1];

        System.out.println("Base sheet of size: " + length + " * " + width);

        int partsLenghts = 0;
        int partsWidth = 0;
        for (int i = 0; i < sheetComposition.length; i++) {
            int[] sheetPart = sheetComposition[i];

            int sheetPartLength = sheetPart[0];
            partsLenghts += sheetPartLength;

            int sheetPartWidth = sheetPart[1];
            partsWidth += sheetPartWidth;

        }

        System.out.println("Verifying partsLength with base length: " + partsLenghts + " should be < " + length);
        System.out.println("Verifying partsWidth with base width: " + partsWidth + " should be < " + width);

        return partsLenghts < length && partsWidth < width;
    }
}