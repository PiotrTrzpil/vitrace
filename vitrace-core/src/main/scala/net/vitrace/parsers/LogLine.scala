package net.vitrace.parsers
import scala.language.experimental.macros

trait LogLine //extends LogLineBase
{
//   def asMap: Map[String, Any] = asMapMacro
//   def asMapMacro: Map[String, Any] = macro Macros.asMap_impl[LogLine]
//
//   def merge(other : LogLine) : LogLine = new LogLine {
//
//      override def asMap : Map[String, Any] = super.asMap ++ other.asMap
//   }
}


object LogLine {
   implicit class Mappable[M <: LogLine](val model: M) extends AnyVal {
      def asMap: Map[String, Any] = macro Macros.asMap_impl[LogLine]
   }

   private object Macros {
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