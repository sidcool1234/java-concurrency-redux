package org.sid.dsa.leetcode;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.TreeMap;

// https://leetcode.com/problems/odd-even-jump/description/
public class OddEvenJump {

    // This is copy pasted.  But I understood the gist
    public static int oddEvenJumps(int[] A) {
        if (A == null || A.length == 0) return 0;
        int n = A.length, res = 1;
        boolean[] higher = new boolean[n], lower = new boolean[n];
        higher[n - 1] = lower[n - 1] = true;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(A[n - 1], n - 1);
        for (int i = n - 2; i >= 0; --i) {
            Map.Entry<Integer, Integer> hi = map.ceilingEntry(A[i]), lo = map.floorEntry(A[i]);
            if (hi != null) higher[i] = lower[hi.getValue()];
            if (lo != null) lower[i] = higher[lo.getValue()];
            if (higher[i]) res++;
            map.put(A[i], i);
        }
        return res;
    }
}
