package au.com.ioof.types;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InstructionTypeTest {

    @Test
    public void testGetInstruction() throws Exception {
        assertEquals(InstructionType.PLACE, InstructionType.getInstruction("PLACE"));
        assertEquals(InstructionType.REPORT, InstructionType.getInstruction("REPORT"));
        assertEquals(null, InstructionType.getInstruction("RIGHT"));

    }
}