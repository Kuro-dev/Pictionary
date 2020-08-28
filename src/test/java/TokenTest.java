import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kurodev.pictionary.logic.net.communication.Participant;
import org.kurodev.pictionary.logic.net.communication.command.tokens.*;
import org.kurodev.pictionary.logic.net.encoding.Encodable;

import java.io.IOException;

/**
 * @author kuro
 **/
@RunWith(JUnitParamsRunner.class)
public class TokenTest extends StreamReaderWriterTest {
    @Test
    public void drawTokenTest() throws IOException {
        Participant p = new Participant("Kuro", 5);
        Encodable expected = new DrawToken(p);
        testStreamRead(expected);
    }

    @Test
    public void timeOutTokenTest() throws IOException {
        Token t = new TimeOutToken();
        testStreamRead(t);
    }

    @Test
    public void timeTokenTest() throws IOException {
        Token t = new TimeToken(26);
        testStreamRead(t);
    }

    @Test
    public void correctGuessTokenTest() throws IOException {
        Participant p = new Participant("Kuro", 5);
        Encodable expected = new CorrectGuessToken(p);
        testStreamRead(expected);
    }

    @Test
    @Parameters({
            "Hello,World;test",
            "this ist a test, this is also a test, some string, different length",
            "",
            "asfaatastdstdrfsz,dszdfszfdsz,sdrfzdsrfzdf,szdfsz,sdfz,fds,zfdszh,dfzdf,z,dfz,dfz,dfz,werz,,rez"
    })
    public void wordSelectTokenTest(String... strings) throws IOException {
        Encodable expected = new SelectWordToken(strings);
        testStreamRead(expected);
    }
}
