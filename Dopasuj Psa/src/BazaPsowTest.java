import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BazaPsowTest {
    BazaPsow bp;
    Pies p, pDodany;
    String nazwa;
    String opis;
    String[] kluczodp = {"ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC"};

    @Before
    public void setUp() {
        bp = BazaPsow.getInstance();
        p = new Pies("labrador", "fajny pies", kluczodp);
        pDodany = new Pies("samoyed", "pies idealny", kluczodp);
        bp.DodajPsa(pDodany);
        nazwa = "labrador";
        opis = "fajny pies";
    }

    @Test
    public void DodawaniePsaTest() {
        bp.DodajPsa(p);
        boolean CzyPsaDodano = bp.baza.containsKey("labrador");
        Assert.assertTrue(CzyPsaDodano);
    }

    @Test
    public void UsuwaniePsaTest() {
        bp.UsunPsa(pDodany);
        boolean CzyPsaUsunieto = bp.baza.containsKey("samoyed");
        Assert.assertFalse(CzyPsaUsunieto);
    }

    @Test
    public void EdytowaniePsaTest() {
        String nowyOpis = "bardzo fajny pies";
        bp.EdytujPsa(pDodany, nowyOpis);
        Assert.assertEquals(pDodany.getOpis(), nowyOpis);
    }

    @Test
    public void CzyPiesWBazieTest() {
        boolean CzyPiesWbp = bp.CzyPiesWBazie(pDodany);
        Assert.assertTrue(CzyPiesWbp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void EdytowaniePsaNieZBazy() {
        bp.UsunPsa(p);
        String nowyOpis = "bardzo fajny pies";
        bp.EdytujPsa(p, nowyOpis);
        Assert.assertEquals(p.getOpis(), nowyOpis);
    }

    @Test
    public void UsuwaniePsaNieZBazy() {
        bp.UsunPsa(p);
    }

    @Test
    public void DodawanieDuplikatu(){
        bp.DodajPsa(p);
        bp.DodajPsa(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void EdycjaPustymOpisu(){
        bp.EdytujPsa(pDodany, "");
    }
}