package new_examples;

import de.wwu.muggl.configuration.Options;
import de.wwu.muggl.vm.classfile.ClassFileException;
import de.wwu.muggl.vm.execution.nativeWrapping.TestablePrintStreamWrapper;
import de.wwu.muli.env.TestableMuliRunner;
import de.wwu.muli.searchtree.ST;
import org.junit.Test;

public class MagicSquareTest {

    @Test
    public final void testGetSolutions() throws InterruptedException, ClassFileException {
        Options.getInst().actualCliPrinting = true;
        ST[] foundTrees = TestableMuliRunner.runApplication("new_examples.MagicSquare");
        TestablePrintStreamWrapper.outputStream().resetBuffer();
    }
}
