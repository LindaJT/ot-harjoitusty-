package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(300);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinArvoAlussaOikein() {
        int vastaus = kortti.saldo();
        assertEquals(300, vastaus);
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(200);
        String vastaus = kortti.toString();
        assertEquals("saldo: 5.0", vastaus);
        
    }
    
    @Test
    public void otaRahaaVahentaaSaldoaOikein() {
        kortti.otaRahaa(100);
        String vastaus = kortti.toString();
        assertEquals("saldo: 2.0", vastaus);
    }
    
    @Test
    public void saldoEiMuutuKunRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(400);
        String vastaus = kortti.toString();
        assertEquals("saldo: 3.0", vastaus);
    }
    
    @Test
    public void otaRahaaPalauttaaTrue() {
        boolean vastaus = kortti.otaRahaa(100);
        assertTrue(vastaus);
    }
    
    @Test
    public void otaRahaaPalauttaaFalse() {
        boolean vastaus = kortti.otaRahaa(400);
        assertFalse(vastaus);
    }
}
