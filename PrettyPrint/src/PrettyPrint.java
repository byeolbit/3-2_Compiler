/**
 * Created by josanggyeong on 2016. 11. 4..
 */

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class PrettyPrint {
    public static void main(String[] args) throws Exception {
        MiniCLexer lexer = new MiniCLexer(new ANTLRFileStream("test"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniCParser parser = new MiniCParser(tokens);
        ParseTree tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();
        MiniCPrintListener listener = new MiniCPrintListener();
        walker.walk(listener, tree);


    }
}
