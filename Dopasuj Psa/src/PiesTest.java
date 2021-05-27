import org.junit.Test;

public class PiesTest {

    @Test(expected = IllegalArgumentException.class)
    public void TworzeniePsaZPustaNazwa(){
        String[] kluczodp = {"ABC"};
        Pies p = new Pies("", "fajny piesio", kluczodp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TworzeniePsaZPustymOpisem(){
        String[] kluczodp = {"ABC"};
        Pies p = new Pies("labrador", "", kluczodp);
    }
}