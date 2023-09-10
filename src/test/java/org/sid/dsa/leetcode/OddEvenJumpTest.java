package org.sid.dsa.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OddEvenJumpTest {
    @Test
    public void shouldOddEvenJump(){
        var input = new int[] {10,13,12,14,15};
        assertEquals(2, OddEvenJump.oddEvenJumps(input));
    }

}