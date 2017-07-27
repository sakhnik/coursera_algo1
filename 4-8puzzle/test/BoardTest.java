/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;

/**
 *
 * @author sakhnik
 */
public class BoardTest {
    
    public BoardTest() {
    }

    @Test
    public void testNeighbors() {
        int[][] blocks = {{1,2,3},{4,0,6},{7,8,5}};

        Board b = new Board(blocks);
        Iterable<Board> neighbors = b.neighbors();
    }
}
