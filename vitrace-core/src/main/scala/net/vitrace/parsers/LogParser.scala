package net.vitrace.parsers

import scala.language.experimental.macros
import scala.util.parsing.combinator.RegexParsers
import pt.LogLine

//trait LogLine
//{
//}
//object LogLine {
//   implicit class Mappable[M <: LogLine](val model: M) extends AnyVal {
//      def asMap: Map[String, Any] = macro Macros.asMap_impl[M]
//   }
//
//   private object Macros {
//      import scala.reflect.macros.Context
//
//      def asMap_impl[T: c.WeakTypeTag](c: Context) = {
//         import c.universe._
//
//         val mapApply = Select(reify(Map).tree, newTermName("apply"))
//         val model = Select(c.prefix.tree, newTermName("model"))
//
//         val pairs = weakTypeOf[T].declarations.collect {
//            case m: MethodSymbol if m.isCaseAccessor =>
//               val name = c.literal(m.name.decoded)
//               val value = c.Expr(Select(model, m.name))
//               reify(name.splice -> value.splice).tree
//         }
//
//         c.Expr[Map[String, Any]](Apply(mapApply, pairs.toList))
//      }
//   }
//}

object LogParser
{
   val anyParser = new AnyParser
   def any = anyParser
}
trait LogParser extends RegexParsers
{
   val spanSize : Int
   def parse(line : String, index : Int) : Option[LogLine]

   def toOption[T](result : ParseResult[T]) : Option[T] = {
      Option.apply(result.getOrElse[T](null.asInstanceOf[T]))
   }
}