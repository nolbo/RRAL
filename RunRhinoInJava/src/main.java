import org.mozilla.javascript.*;
import org.mozilla.javascript.annotations.JSStaticFunction;
import java.util.Scanner;

public class main {
    public static String Run(String src){
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        rhino.setLanguageVersion(Context.VERSION_ES6);
        try{
            Scriptable scope = new ImporterTopLevel(rhino);
            ScriptableObject.putProperty(scope, "ctx", src);
            ScriptableObject.defineClass(scope, Api.class);
            return rhino.evaluateString(scope, src, "JavaScript", 1, null).toString();
        }catch (Exception e){
            return e.toString();
        }finally {
            Context.exit();
        }
    }

    public static class Api extends ScriptableObject{

        @Override
        public String getClassName() {
            return "Api";
        }

        @JSStaticFunction
        public static String test(){ //must be 'static'
            return "Hello World!";
        }
    }

    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        System.out.print("Input.. > ");
        String src = scn.next();
        System.out.println(Run(src));
    }
}
