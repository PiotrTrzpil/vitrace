import com.digitaldoodles.rex.CharRange
import com.digitaldoodles.rex.CharRange
import net.vitrace.parsers._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

import scala.language.experimental.macros


// This imports the basic constructors and predefined patterns.
import com.digitaldoodles.rex._

import scala.util.parsing.combinator.RegexParsers
// This imports a single (as of Rex 0,7) implicit conversion that allows strings to be used
// as literals in Rex expressions.
//import com.digitaldoodles.rex.Implicits._
// This imports objects that contain further predefined patterns; see the API documentation for details.

@RunWith(classOf[JUnitRunner])
class LogLineTest extends FunSuite with RegexParsers
{
   case class LineT1(message : String) extends LogLine
   case class LineT2(notes : String) extends LogLine

   test("merge")
   {
      //val logLine = LineT1("mess") merge LineT2("n")
      val dd = LineT1("mess").asMap
      assert(LineT1("mess").asMap.get("message") === Some("mess"))
     // assert(logLine.asMap.get("message") === Some("mess"))
     // assert(logLine.asMap.get("notes") === Some("n"))
   }




}