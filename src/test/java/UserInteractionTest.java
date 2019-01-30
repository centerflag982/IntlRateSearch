import com.centerflag982.IntlRateSearch.UserInteraction;
import org.junit.Test;

public class UserInteractionTest {
    @Test
    public void whatExceptionDoesThisThrow(){
        UserInteraction uiTest = new UserInteraction();
        uiTest.greet("blah");
    }
}
