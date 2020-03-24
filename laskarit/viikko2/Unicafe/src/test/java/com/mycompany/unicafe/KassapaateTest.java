import com.mycompany.unicafe.Kassapaate;
import com.mycompany.unicafe.Maksukortti;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    Kassapaate paate;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
    }
    
    @Test
    public void oikeaMaaraRahaaAluksi() {
        int vastaus = paate.kassassaRahaa();

        assertEquals(100000, vastaus);
    }
    
    @Test
    public void oikeaMaaraMuutyjaMaukkaitaAluksi() {
        int vastaus = paate.maukkaitaLounaitaMyyty();

        assertEquals(0, vastaus);
    }
    
    @Test
    public void oikeaMaaraMuutyjaEdullisiaAluksi() {
        int vastaus = paate.edullisiaLounaitaMyyty();

        assertEquals(0, vastaus);
    }
    
    @Test
    public void summaOikeinEdullisenJalkeenKateisosto() {
        paate.syoEdullisesti(240);
        int vastaus = paate.kassassaRahaa();
        assertEquals(100240, vastaus);
    }
    
    @Test
    public void oikeaMaaraEdullisiaKateisosto() {
        paate.syoEdullisesti(240);
        int vastaus = paate.edullisiaLounaitaMyyty();
        assertEquals(1, vastaus);
    }
    
    @Test
    public void summaOikeinMaukkaanJalkeenKateisosto() {
        paate.syoMaukkaasti(400);
        int vastaus = paate.kassassaRahaa();
        assertEquals(100400, vastaus);
    }
    
    @Test
    public void oikeaMaaraMaukkaitaKateisosto() {
        paate.syoMaukkaasti(400);
        int vastaus = paate.maukkaitaLounaitaMyyty();
        assertEquals(1, vastaus);
    }
    
    @Test
    public void vaihtorahaOikeinKateisosto() {
        int vastaus = paate.syoMaukkaasti(500);
        assertEquals(100, vastaus);
    }
    
    @Test
    public void summaOikeinMaksuLiianPieni() {
        paate.syoEdullisesti(20);
        int vastaus = paate.kassassaRahaa();
        assertEquals(100000, vastaus);
    }
    
    @Test
    public void syoEdullisestiVaihtorahaOikeinMaksuLiaanPieni() {
        int vastaus = paate.syoEdullisesti(20);
        assertEquals(20, vastaus);
    }
    
    @Test
    public void syoMaukkaastiVaihtorahaOikeinMaksuLiaanPieni() {
        int vastaus = paate.syoMaukkaasti(20);
        assertEquals(20, vastaus);
    }
    
    @Test
    public void lounaidenMaaraMaksuLiianPieni() {
        paate.syoEdullisesti(20);
        int vastaus = paate.edullisiaLounaitaMyyty();
        assertEquals(0, vastaus);
    }
    
    @Test
    public void syoEdullisestiKorttiostoTarpeeksiRahaaPalauttaaTrue() {
        kortti = new Maksukortti(300);
        boolean vastaus = paate.syoEdullisesti(kortti);
        assertTrue(vastaus);
    }
    
    @Test
    public void syoEdullisestiKorttiostoTarpeeksiRahaaKortiltaVaheneeRahaa() {
        kortti = new Maksukortti(300);
        paate.syoEdullisesti(kortti);
        int vastaus = kortti.saldo();
        assertEquals(60, vastaus);
    }
    
    @Test
    public void syoEdullisestiKorttiostoEdullistenMaaraKasvaa() {
        kortti = new Maksukortti(300);
        paate.syoEdullisesti(kortti);
        int vastaus = paate.edullisiaLounaitaMyyty();
        assertEquals(1, vastaus);
    }
    
    @Test
    public void syoEdullisestiKorttiostoEiTarpeeksiRahaaPalauttaaFalse(){
        kortti = new Maksukortti(100);
        boolean vastaus = paate.syoEdullisesti(kortti);
        assertFalse(vastaus);
    }
    
    @Test 
    public void syoEdullisestiKorttiostoEiTarpeeksiRahaaKortiltaEiLahdeRahaa() {
        kortti = new Maksukortti(100);
        paate.syoEdullisesti(kortti);
        int vastaus = kortti.saldo();
        assertEquals(100, vastaus);
    }
    
    @Test
    public void syoEdullisestiKorttiostoEdullistenMaaraEiKasva() {
        kortti = new Maksukortti(100);
        paate.syoEdullisesti(kortti);
        int vastaus = paate.edullisiaLounaitaMyyty();
        assertEquals(0, vastaus);
    }
     
        @Test
    public void syoMaukkaastiKorttiostoTarpeeksiRahaaPalauttaaTrue() {
        kortti = new Maksukortti(500);
        boolean vastaus = paate.syoMaukkaasti(kortti);
        assertTrue(vastaus);
    }
    
    @Test
    public void syoMaukkaastiKorttiostoTarpeeksiRahaaKortiltaVaheneeRahaa() {
        kortti = new Maksukortti(500);
        paate.syoMaukkaasti(kortti);
        int vastaus = kortti.saldo();
        assertEquals(100, vastaus);
    }
    
    @Test
    public void syoMaukkaastiKorttiostoMaukkaidenMaaraKasvaa() {
        kortti = new Maksukortti(500);
        paate.syoMaukkaasti(kortti);
        int vastaus = paate.maukkaitaLounaitaMyyty();
        assertEquals(1, vastaus);
    }
    
    @Test
    public void syoMaukkaastiKorttiostoEiTarpeeksiRahaaPalauttaaFalse(){
        kortti = new Maksukortti(100);
        boolean vastaus = paate.syoMaukkaasti(kortti);
        assertFalse(vastaus);
    }
    
    @Test 
    public void syoMaukkaastiKorttiostoEiTarpeeksiRahaaKortiltaEiLahdeRahaa() {
        kortti = new Maksukortti(100);
        paate.syoMaukkaasti(kortti);
        int vastaus = kortti.saldo();
        assertEquals(100, vastaus);
    }
    
    @Test
    public void syoMaukkaastiKorttiostoMaukkaidenMaaraEiKasva() {
        kortti = new Maksukortti(100);
        paate.syoMaukkaasti(kortti);
        int vastaus = paate.maukkaitaLounaitaMyyty();
        assertEquals(0, vastaus);
    }
    
    @Test
    public void kassanSaldoEiMuutuKortillaOstettaessa() {
        kortti = new Maksukortti(500);
        paate.syoMaukkaasti(kortti);
        int vastaus = paate.kassassaRahaa();
        assertEquals(100000, vastaus);
    }
      
    @Test
    public void kortilleLadataanRahaaKortinSaldoMuuttuu() {
        kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, 100);
        int vastaus = kortti.saldo();
        assertEquals(100, vastaus);
    }
    
    @Test
    public void kortilleLadataanNegatiivinenLukuKassanSaldoEiMuutu() {
        kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, -5);
        int vastaus = paate.kassassaRahaa();
        assertEquals(100000, vastaus);
    }
    
    @Test
    public void kortilleLadataanNegatiivinenLukuKortinSaldoEiMuutu() {
        kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, -5);
        int vastaus = kortti.saldo();
        assertEquals(0, vastaus);
    }
    
    @Test
    public void kortilleLadataanRahaaKassanSaldoMuuttuu() {
        kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, 100);
        int vastaus = paate.kassassaRahaa();
        assertEquals(100100, vastaus);
    }

}
