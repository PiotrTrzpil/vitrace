package pt

import scala.util.matching.Regex
import scala.util.matching.Regex.Match

class LineToken(val name:String, val regex:Regex)
{

   def fingValue(lineFragment:String) : (Any, Int) =
   {
      val patternMatch: Match = regex.findFirstMatchIn(lineFragment)
        .getOrElse(throw new RuntimeException("Token "+regex+" not found"))
      if(patternMatch.groupCount == 0)
      {
         (patternMatch.matched, patternMatch.end(0))
      }
      else
      {
         (patternMatch.group(1), patternMatch.end(0))
      }
   }
}

