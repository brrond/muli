package brrond;

import de.wwu.muggl.vm.classfile.ClassFileException;
import de.wwu.muggl.vm.execution.nativeWrapping.TestablePrintStreamWrapper;
import de.wwu.muli.env.TestableMuliRunner;
import de.wwu.muli.searchtree.ST;
import org.junit.Test;

public class HelloWorldTest {

    @Test
    public final void testGetSolutions() throws InterruptedException, ClassFileException {
        ST[] foundTrees = TestableMuliRunner.runApplication("brrond.HelloWorld");
        TestablePrintStreamWrapper.outputStream().resetBuffer(); // To reset output stream
    }
}
