package brrond;

import de.wwu.muggl.configuration.Options;
import de.wwu.muggl.vm.classfile.ClassFileException;
import de.wwu.muggl.vm.execution.nativeWrapping.TestablePrintStreamWrapper;
import de.wwu.muli.Muli;
import de.wwu.muli.env.TestableMuliRunner;
import de.wwu.muli.searchtree.ST;
import de.wwu.muli.solution.Solution;
import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;

public class SimpleTest {



    @Test
    public final void testGetSolutions() throws InterruptedException, ClassFileException {
        Options.getInst().actualCliPrinting = true;
        ST[] foundTrees = TestableMuliRunner.runApplication("brrond.Simple");
        TestablePrintStreamWrapper.outputStream().resetBuffer(); // To reset output stream
    }
}
