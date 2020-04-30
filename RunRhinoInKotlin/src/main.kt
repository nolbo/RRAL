import org.mozilla.javascript.*
import org.mozilla.javascript.annotations.JSStaticFunction
import java.util.*

fun Run(src : String): Any? {
    var rhino : Context = Context.enter()
    rhino.optimizationLevel = -1
    rhino.languageVersion = Context.VERSION_ES6
    return try{
        var scope : Scriptable = ImporterTopLevel(rhino)
        ScriptableObject.putProperty(scope, "ctx", src)
        ScriptableObject.defineClass(scope, Api().javaClass)
        /* compile source
        * var script : Script = rhino.compileString(src, "JavaScript", 1, null)
        * return script.exec(rhino, scope)
        *
        */
        rhino.evaluateString(scope, src, "JavaScript", 1, null)
    }catch (error : Exception){
        error
    }finally {
        Context.exit()
    }
}

class Api : ScriptableObject() {
    override fun getClassName(): String {
        return "Api"
    }

    companion object {
        @JvmStatic
        @JSStaticFunction
        fun test() : String {
            return "Hello world!"
        }
    }
}




fun main(args : Array<String>){
    var scn : Scanner = Scanner(System.`in`)
    print("Input.. > ")
    var src : String = scn.next()
    println(Run(src))
}


