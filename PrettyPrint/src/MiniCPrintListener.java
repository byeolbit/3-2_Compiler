import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 * Created by josanggyeong on 2016. 11. 4..
 */
public class MiniCPrintListener extends MiniCBaseListener {

    private ParseTreeProperty<String> newCode = new ParseTreeProperty<>();

    @Override
    public void exitDecl(MiniCParser.DeclContext ctx) {
        newCode.put(ctx, newCode.get(ctx.getChild(0)));
    }

    @Override
    public void exitVar_decl(MiniCParser.Var_declContext ctx) {
        String v;
        if (isArray(ctx))
            v = newCode.get(ctx.type_spec()) + " " + ctx.IDENT().getText() + ctx.getChild(3).getText() + ctx.LITERAL().getText() + ctx.getChild(5).getText() + ctx.getChild(5).getText() + "\n";
        else if (isAssigned(ctx))
            v = newCode.get(ctx.type_spec()) + " " + ctx.IDENT().getText() + " " + ctx.getChild(2).getText() + " " + ctx.LITERAL().getText() + ctx.getChild(4).getText() + "\n";
        else
            v = newCode.get(ctx.type_spec()) + " " + ctx.IDENT().getText() + ctx.getChild(2).getText() + "\n";

        newCode.put(ctx, v);
    }

    private boolean isArray(MiniCParser.Var_declContext ctx) {
        return ctx.getChildCount() == 6 && ctx.getChild(3).getText().equals("[");
    }

    private boolean isAssigned(MiniCParser.Var_declContext ctx) {
        return ctx.getChildCount() == 5 && ctx.getChild(2).getText().equals("=");
    }

    @Override
    public void exitProgram(MiniCParser.ProgramContext ctx) {
        String p = "";
        for (MiniCParser.DeclContext c : ctx.decl()) {
            p += newCode.get(c);
        }

        newCode.put(ctx, p);
        System.out.print(newCode.get(ctx));
    }


    private boolean isVOID(MiniCParser.Type_specContext ctx) {
        return ctx.getChildCount() == 1 && ctx.VOID() != null;
    }

    @Override
    public void exitType_spec(MiniCParser.Type_specContext ctx) {
        String t;

        if (isVOID(ctx))
            t = ctx.VOID().getText();
        else
            t = ctx.INT().getText();

        newCode.put(ctx, t);
    }

    @Override
    public void exitFun_decl(MiniCParser.Fun_declContext ctx) {
        newCode.put(ctx, newCode.get(ctx.type_spec()) + " " + ctx.IDENT().getText() + "(" + newCode.get(ctx.params()) + ")\n" + newCode.get(ctx.compound_stmt()));
    }

    private boolean isNoParam(MiniCParser.ParamsContext ctx) {
        return ctx.getChildCount() == 0;
    }

    private boolean isVOID(MiniCParser.ParamsContext ctx) {
        return ctx.getChildCount() == 1 && ctx.VOID() != null;
    }

    @Override
    public void exitParams(MiniCParser.ParamsContext ctx) {
        String p = "";

        if (!isNoParam(ctx) && !isVOID(ctx)) {
            for (MiniCParser.ParamContext anCtx : ctx.param()) {
                p += newCode.get(anCtx) + ", ";
            }
            p = p.substring(0, p.length() - 2);
        }
        newCode.put(ctx, p);
    }

    @Override
    public void exitParam(MiniCParser.ParamContext ctx) {
        String p = newCode.get(ctx.type_spec()) + " " + ctx.IDENT().getText();
        newCode.put(ctx, p);
    }

    @Override
    public void exitStmt(MiniCParser.StmtContext ctx) {
        newCode.put(ctx, newCode.get(ctx.getChild(0)));
    }

    @Override
    public void exitWhile_stmt(MiniCParser.While_stmtContext ctx) {
        String result;

        String[] indentedStmt;
        String stmt = newCode.get(ctx.stmt());
        String newStmt = "";
        String indent = "....";
        indentedStmt = stmt.split("\n");

        if (isCompoundStmt(ctx.stmt())) {
            for (String anIndentedStmt : indentedStmt) newStmt += (anIndentedStmt + "\n");
        } else {
            newStmt += ("{\n");
            //newStmt += indent + stmt + "\n";
            for (String anIndentedStmt : indentedStmt) newStmt += (indent + anIndentedStmt + "\n");
            newStmt += ("}\n");
        }

        newCode.put(ctx.stmt(), newStmt);
        result = ctx.getChild(0) + " " + ctx.getChild(1) + newCode.get(ctx.expr()) + ctx.getChild(3) + "\n" + newStmt;
        newCode.put(ctx, result);
    }

    private boolean isCompoundStmt(MiniCParser.StmtContext stmt) {
        return stmt.compound_stmt() != null;
    }

    @Override
    public void exitCompound_stmt(MiniCParser.Compound_stmtContext ctx) {
        String result = "{\n";
        int numberOfstmt = ctx.getChildCount();
        for (int i = 1; i < numberOfstmt - 1; i++) {

            String[] indentedStmt;
            String stmt = newCode.get(ctx.getChild(i));
            String newStmt = "";
            String indent = "....";
            indentedStmt = stmt.split("\n");

            for (String anIndentedStmt : indentedStmt)
                newStmt += (indent + anIndentedStmt + "\n");

            result += newStmt;
        }
        result += "}\n";
        newCode.put(ctx, result);
    }

    boolean isVariable(MiniCParser.Local_declContext ctx) {
        return ctx.getChildCount() == 3;
    }

    @Override
    public void exitLocal_decl(MiniCParser.Local_declContext ctx) {
        String s1 = null, s2 = null;
        if (isVariable(ctx)) {
            s1 = newCode.get(ctx.type_spec());
            s2 = ctx.IDENT().getText();
            newCode.put(ctx, s1 + " " + s2 + ctx.getChild(2) + "\n");
        } else {
            s1 = newCode.get(ctx.type_spec());
            s2 = " " + ctx.IDENT().getText() + ctx.getChild(2) + ctx.LITERAL().getText() + ctx.getChild(4) + ctx.getChild(5);
            newCode.put(ctx, s1 + s2 + "\n");
        }
    }

    @Override
    public void exitIf_stmt(MiniCParser.If_stmtContext ctx) {
        String e1, s1;
        e1 = ctx.getChild(0).getText() + " " + ctx.getChild(1).getText() + newCode.get(ctx.expr()) + ctx.getChild(3).getText() + "\n";
        s1 = newCode.get(ctx.stmt(0));

        String[] indentedStmt;
        String newStmt = "";
        String indent = "....";
        indentedStmt = s1.split("\n");

        if (isCompoundStmt(ctx.stmt(0))) {
            for (String anIndentedStmt : indentedStmt)
                newStmt += (anIndentedStmt + "\n");
        } else {
            newStmt += "{\n";
            for (String anIndentedStmt : indentedStmt)
                newStmt += (indent + anIndentedStmt + "\n");
            newStmt += "}\n";
        }
        newCode.put(ctx.stmt(0), newStmt);
        newCode.put(ctx, e1 + newCode.get(ctx.stmt(0)));
    }

    @Override
    public void exitReturn_stmt(MiniCParser.Return_stmtContext ctx) {
        if (ctx.expr() != null)
            newCode.put(ctx, ctx.RETURN().getText() + " " + newCode.get(ctx.expr()) + ctx.getChild(2) + "\n");
        else
            newCode.put(ctx, ctx.RETURN().getText() + ctx.getChild(1) + "\n");
    }

    @Override
    public void exitExpr(MiniCParser.ExprContext ctx) {
        String s1 = null, s2 = null, op;

        if (isIDENT(ctx)) {
            newCode.put(ctx, ctx.IDENT().getText());

        } else if (isLITERAL(ctx)) {
            newCode.put(ctx, ctx.LITERAL().getText());

        } else if (isBinaryOperation(ctx)) {

            s1 = newCode.get(ctx.expr(0));
            s2 = newCode.get(ctx.expr(1));
            op = ctx.getChild(1).getText();
            newCode.put(ctx, s1 + " " + op + " " + s2);

        } else if (isAssign(ctx)) {
            s1 = ctx.IDENT().getText();
            s2 = newCode.get(ctx.expr(0));
            op = ctx.getChild(1).getText();
            newCode.put(ctx, s1 + " " + op + " " + s2);

        } else if (isFrontOperation(ctx)) {
            s1 = newCode.get(ctx.expr(0));
            op = ctx.getChild(0).getText();
            newCode.put(ctx, op + s1);

        } else if (isInBracket(ctx)) {
            s1 = newCode.get(ctx.expr(0));
            newCode.put(ctx, ctx.getChild(0).getText() + s1 + ctx.getChild(2));

        } else if (isArray(ctx)) {
            s1 = ctx.IDENT().getText();
            s2 = newCode.get(ctx.expr(0));
            newCode.put(ctx, s1 + " " + ctx.getChild(1) + s2 + ctx.getChild(3));

        } else if (isArrayExpr(ctx)) {
            s1 = ctx.IDENT().getText() + ctx.getChild(1) + newCode.get(ctx.expr(0)) + ctx.getChild(3);
            s2 = newCode.get(ctx.expr(1));
            newCode.put(ctx, s1 + " " + ctx.getChild(4) + " " + s2);

        } else if (isFunc(ctx)) {
            s1 = ctx.IDENT().getText();
            s2 = newCode.get(ctx.args());
            newCode.put(ctx, s1 + ctx.getChild(1) + s2 + ctx.getChild(3));
        }


    }

    private boolean isAssign(MiniCParser.ExprContext ctx) {
        return (ctx.getChildCount() == 3) && (ctx.IDENT() != null) && (ctx.expr(0) != null);
    }

    private boolean isLITERAL(MiniCParser.ExprContext ctx) {
        return (ctx.getChildCount() == 1) && (ctx.LITERAL() != null);
    }

    private boolean isIDENT(MiniCParser.ExprContext ctx) {
        return (ctx.getChildCount() == 1) && (ctx.IDENT() != null);
    }

    private boolean isBinaryOperation(MiniCParser.ExprContext ctx) {
        return (ctx.getChildCount() == 3) && (ctx.expr(0) != null) && (ctx.expr(1) != null);
    }

    private boolean isFrontOperation(MiniCParser.ExprContext ctx) {
        return (ctx.getChildCount() == 2) && (ctx.getChild(1) != ctx.expr());
    }

    private boolean isInBracket(MiniCParser.ExprContext ctx) {
        return (ctx.getChildCount() == 3) && (ctx.getChild(1) == ctx.expr(0));
    }

    private boolean isArrayExpr(MiniCParser.ExprContext ctx) {
        return (ctx.getChildCount() == 6) && ctx.getChild(1).getText().equals("[");
    }

    private boolean isArray(MiniCParser.ExprContext ctx) {
        return (ctx.getChildCount() == 4) && ctx.getChild(1).getText().equals("[");
    }

    private boolean isFunc(MiniCParser.ExprContext ctx) {
        return (ctx.getChildCount() == 4) && (ctx.getChild(1).getText().equals("("));
    }

    @Override
    public void exitExpr_stmt(MiniCParser.Expr_stmtContext ctx) {
        newCode.put(ctx, newCode.get(ctx.getChild(0)) + ctx.getChild(1).getText() + "\n");
    }

    @Override
    public void exitArgs(MiniCParser.ArgsContext ctx) {
        String a = "";
        for (MiniCParser.ExprContext c : ctx.expr()) {
            a += (newCode.get(c) + ", ");
        }
        a = a.substring(0, a.length() - 2);
        newCode.put(ctx, a);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
    }
}
