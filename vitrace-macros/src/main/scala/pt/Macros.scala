package pt

import scala.language.experimental.macros
/*
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
object Macros
{

   def getMap_impl[T: c.WeakTypeTag](c: Context): c.Expr[Map[String, Any]] = {
      import c.universe._

      val tpe = weakTypeOf[T]

      // Filter members that start with "value", which are val fields
      val members = tpe.members.toList.filter(m => !m.isMethod && m.toString.startsWith("value"))

      // Create ("fieldName", field) tuples to construct a map from field names to fields themselves
      val tuples =
         for {
            m <- members
            fieldString = Literal(Constant(m.toString.replace("value ", "")))
            field = Ident(m)
         } yield (fieldString, field)

      val mappings = tuples.toMap

      /* Parse the string version of the map [i.e. Map("posts" -> (posts), "age" -> (age), "name" -> (name))] to get the AST
       * for the map, which is generated as:
       *
       * Apply(Ident(newTermName("Map")),
       *   List(
       *     Apply(Select(Literal(Constant("posts")), newTermName("$minus$greater")), List(Ident(newTermName("posts")))),
       *     Apply(Select(Literal(Constant("age")), newTermName("$minus$greater")), List(Ident(newTermName("age")))),
       *     Apply(Select(Literal(Constant("name")), newTermName("$minus$greater")), List(Ident(newTermName("name"))))
       *   )
       * )
       *
       * which is equivalent to Map("posts".$minus$greater(posts), "age".$minus$greater(age), "name".$minus$greater(name))
       */
      c.Expr[Map[String, Any]](c.parse(mappings.toString))
   }
}
*/