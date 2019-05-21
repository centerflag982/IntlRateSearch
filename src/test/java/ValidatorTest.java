import com.centerflag982.IntlRateSearch.ImportValidator;
import org.junit.Test;

public class ValidatorTest {


    @Test
    public void testValidator(){
        ImportValidator fileIO = new ImportValidator();
        String testString = "LH,Lufthansa,JFK,ABZ,95.0,2.35,2.25,1.97,1.88,1.88,1.79,N/A,N/A,20191018,";
        System.out.println(fileIO.validateString(testString));
    }


/*
    @Test
    public void testIataCheck(){
        ImportValidator validator = new ImportValidator();
        String testString = "";
        System.out.println(validator.checkIata(testString));
    }

    @Test
    public void testAirportCheck(){
        ImportValidator validator = new ImportValidator();
        String testString = "";
        System.out.println(validator.checkAirport(testString));
    }

    @Test
    public void testRateCheck(){
        ImportValidator validator = new ImportValidator();
        String testString = "";
        System.out.println(validator.checkRateFields(testString));
    }

    @Test
    public void testExpirationCheck(){
        ImportValidator validator = new ImportValidator();
        String testString = "";
        System.out.println(validator.checkExpiration(testString));
    }

 */
}
