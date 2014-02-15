package pt

import scala.language.experimental.macros

//import pt.Macros

trait LogLine //extends LogLineBase
{
   def toMap: Map[String, Any] = getCCParams(this)

   def getCCParams(cc: AnyRef) =
      (Map[String, Any]() /: cc.getClass.getDeclaredFields) {(a, f) =>
         f.setAccessible(true)
         a + (f.getName -> f.get(cc))
      }
   //def asMapMacro: Map[String, Any] = macro LogLine.Macros.asMap_impl[LogLine]
//
   def merge(other : LogLine) : LogLine = new LogLine {

      override def toMap : Map[String, Any] = super.toMap ++ other.toMap
   }
 //lazy val asMap = new Mappable[LogLine](this).toMap
}
object EmptyLine extends LogLine{

}

object LogLine {
//   implicit class Mappable[M <: LogLine](val model: M) extends AnyVal {
//      def toMap: Map[String, Any] = macro Macros.asMap_impl[M]


//}

   object Macros {
      import scala.reflect.macros.Context

      def asMap_impl[T: c.WeakTypeTag](c: Context) = {
         import c.universe._

         val mapApply = Select(reify(Map).tree, newTermName("apply"))
         val model = Select(c.prefix.tree, newTermName("model"))

         val pairs = weakTypeOf[T].declarations.collect {
            case m: MethodSymbol if m.isCaseAccessor =>
               val name = c.literal(m.name.decoded)
               val value = c.Expr(Select(model, m.name))
               reify(name.splice -> value.splice).tree
         }

         c.Expr[Map[String, Any]](Apply(mapApply, pairs.toList))
      }
   }
}