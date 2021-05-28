import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BazaPsowTest {
    Baza bp;
    Pies p, pDodany;
    String nazwa;
    String opis;
    String[] kluczodp = {"ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC"};

    @Before
    public void setUp() {
        bp = Baza.getInstance();
        p = new Pies("labrador", "fajny pies", kluczodp);
        pDodany = new Pies("samoyed", "pies idealny", kluczodp);
        bp.DodajZwierze(pDodany);
        nazwa = "labrador";
        opis = "fajny pies";
    }

    @Test
    public void DodawaniePsaTest() {
        bp.DodajZwierze(p);
        boolean CzyPsaDodano = bp.baza.containsKey("labrador");
        Assert.assertTrue(CzyPsaDodano);
    }

    @Test
    public void UsuwaniePsaTest() {
        bp.UsunZwierze(pDodany);
        boolean CzyPsaUsunieto = bp.baza.containsKey("samoyed");
        Assert.assertFalse(CzyPsaUsunieto);
    }

    @Test
    public void EdytowaniePsaTest() {
        String nowyOpis = "bardzo fajny pies";
        bp.EdytujZwierze(pDodany, nowyOpis);
        Assert.assertEquals(pDodany.getOpis(), nowyOpis);
    }

    @Test
    public void CzyPiesWBazieTest() {
        boolean CzyPiesWbp = bp.CzyZwierzeWBazie(pDodany);
        Assert.assertTrue(CzyPiesWbp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void EdytowaniePsaNieZBazy() {
        bp.UsunZwierze(p);
        String nowyOpis = "bardzo fajny pies";
        bp.EdytujZwierze(p, nowyOpis);
        Assert.assertEquals(p.getOpis(), nowyOpis);
    }

    @Test
    public void UsuwaniePsaNieZBazy() {
        bp.UsunZwierze(p);
    }

    @Test
    public void DodawanieDuplikatu(){
        bp.DodajZwierze(p);
        bp.DodajZwierze(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void EdycjaPustymOpisu(){
        bp.EdytujZwierze(pDodany, "");
    }
}
